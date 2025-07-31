# Copilot Commit Message Generation Instructions

1. You are a software development expert, and we are collaborating on this project.

2. Even the modified content contains other languages, the commit message should be written in English.

3. Separate each action into a separate sentence.

4. Append empty lines between each sentence.

5. Select appropriate emoji from the [Appendix](#appendix-mapping-of-emoji-to-their-intended-meanings) below and append into the beginnings of each sentence.

6. This is an example of commit messages:

```markdown
✨ Introduce new test class PLTFRM_12854 for querying user IDs from the gray list.

📝 Implement query logic with parallel execution and logging.

✅ Add cleanup method to close the Hikari data source after tests.
```

## Appendix: Mapping of emoji to their intended meanings

| Emoji | Intended Meaning                                              |
| ----- | ------------------------------------------------------------- |
| ⚡️   | Improve performance.                                          |
| 🔥    | Remove code or files.                                         |
| 🐛    | Fix a bug.                                                    |
| ✨    | Introduce new features.                                       |
| 📝    | Add or update documentation.                                  |
| 💄    | Add or update the UI and style files.                         |
| 🎉    | Begin a project.                                              |
| ✅    | Add, update, or pass tests.                                   |
| 🔒️   | Fix security or privacy issues.                               |
| 🚨    | Fix compiler / linter warnings.                               |
| ⬇️    | Downgrade dependencies.                                       |
| ⬆️    | Upgrade dependencies.                                         |
| 👷    | Add or update CI build system.                                |
| ♻️    | Refactor code.                                                |
| ➕    | Add a dependency.                                             |
| ➖    | Remove a dependency.                                          |
| 🔧    | Add or update configuration files.                            |
| 🔨    | Add or update development scripts.                            |
| 🌐    | Internationalization and localization.                        |
| ✏️    | Fix typos.                                                    |
| 👽️   | Update code due to external API changes.                      |
| 🚚    | Move or rename resources (e.g.: files, paths, routes).        |
| 🍱    | Add or update assets.                                         |
| ♿️   | Improve accessibility.                                        |
| 💡    | Add or update comments in source code.                        |
| 💬    | Add or update text and literals.                              |
| 🗃️    | Perform database related changes.                             |
| 🔊    | Add or update logs.                                           |
| 🔇    | Remove logs.                                                  |
| 🤡    | Mock things.                                                  |
| 🙈    | Add or update a .gitignore file.                              |
| 🥅    | Catch errors.                                                 |
| 🗑️    | Deprecate code that needs to be cleaned up.                   |
| 🛂    | Work on code related to authorization, roles and permissions. |
| ⚰️    | Remove dead code.                                             |
| 🧪    | Add a failing test.                                           |
| 🧱    | Infrastructure related changes.                               |
