package com.example.common;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import com.tngtech.archunit.library.Architectures;
import com.tngtech.archunit.library.dependencies.SlicesRuleDefinition;
import org.junit.jupiter.api.Test;

import java.util.List;

public class DependencyTests {
    private static final String BASE_PACKAGE = "com.example";

    @Test
    void no_cycles_between_packages() {
        final JavaClasses importedClasses = new ClassFileImporter().importPackages(BASE_PACKAGE);

        SlicesRuleDefinition
                .slices().matching(BASE_PACKAGE + ".(*)..")
                .should().beFreeOfCycles()
                .check(importedClasses);
    }

    @Test
    void clean_architecture_package_dependency_validation() {
        final JavaClasses importedClasses = new ClassFileImporter().importPackages(BASE_PACKAGE);

        Architectures
                .layeredArchitecture().consideringOnlyDependenciesInLayers()

                .layer("Presentation").definedBy(
                        BASE_PACKAGE + "..presentation..converter..",
                        BASE_PACKAGE + "..presentation..dto..",
                        BASE_PACKAGE + "..presentation..protocol..",
                        BASE_PACKAGE + "..presentation..request..",
                        BASE_PACKAGE + "..presentation..response..",
                        BASE_PACKAGE + "..presentation..route.."
                )
                .layer("Application").definedBy(
                        BASE_PACKAGE + "..application.adapter..",
                        BASE_PACKAGE + "..application.port..",
                        BASE_PACKAGE + "..application.usecase.."
                )
                .layer("Domain").definedBy(
                        BASE_PACKAGE + "..domain.entity..",
                        BASE_PACKAGE + "..domain.event..",
                        BASE_PACKAGE + "..domain.repository..",
                        BASE_PACKAGE + "..domain.vo.."
                )
                .layer("Infrastructure").definedBy(
                        BASE_PACKAGE + "..infrastructure.config..",
                        BASE_PACKAGE + "..infrastructure.persistence..",
                        BASE_PACKAGE + "..infrastructure.repository..",
                        BASE_PACKAGE + "..infrastructure.eventbus..")

                .whereLayer("Presentation").mayNotBeAccessedByAnyLayer()
                .whereLayer("Application").mayOnlyBeAccessedByLayers("Presentation", "Infrastructure")
                .whereLayer("Domain").mayOnlyBeAccessedByLayers("Application", "Infrastructure")

                .ensureAllClassesAreContainedInArchitecture()
                // but ignore com.example.common.. and com.example.*Application
                .ensureAllClassesAreContainedInArchitectureIgnoring(createArchitectureIgnorePredicate())

                .check(importedClasses);
    }

    @Test
    void domains_should_only_depend_on_other_domains_ipc_package() {
        final JavaClasses importedClasses = new ClassFileImporter().importPackages(BASE_PACKAGE);

//        final List<String> domains = List.of("member", "product", "order");
        final List<String> domains = List.of("member");

        for (String domain1 : domains) {
            for (String domain2 : domains) {
                if (domain1.equals(domain2)) {
                    continue;
                }

                ArchRuleDefinition
                        .classes().that().resideInAPackage(BASE_PACKAGE + "." + domain1 + "..")
                        .should().onlyDependOnClassesThat()
                        .resideInAnyPackage(
                                BASE_PACKAGE + "." + domain1 + "..",
                                BASE_PACKAGE + "." + domain2 + ".presentation.ipc..",
                                BASE_PACKAGE + ".common..",
                                "io.swagger..",
                                "java..",
                                "jakarta..",
                                "org..",
                                "lombok..")
                        .check(importedClasses);
            }
        }
    }

    private DescribedPredicate<JavaClass> createArchitectureIgnorePredicate() {
        return new DescribedPredicate<>("Classes to ignore in architecture validation") {
            @Override
            public boolean test(final JavaClass javaClass) {
                return isInCommonPackage(javaClass) || isApplication(javaClass);
            }

            private boolean isInCommonPackage(final JavaClass javaClass) {
                return javaClass.getName().contains(".common.");
            }

            private boolean isApplication(final JavaClass javaClass) {
                return javaClass.getName().endsWith("Application");
            }
        };
    }

}
