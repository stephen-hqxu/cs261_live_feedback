# CS261-Project
This is our CS261 project.

## Getting Started

First thing first, go to our **Teams Files**, download *data.db*, a clean version of database with schema loaded up, which has already been pinned to the top. 

Then put the this into /app folder.

You only need to do this once, all subsequent commits will no longer have *data.db* included, so everyone can have their own copy of the database without running into merge conflicts.

> :warning: Please inform everyone if *schema.sql* has been changed.

---

Navigate to the project root directory where the **gradlew** files are located, then:

- Windows:

In cmd or Powershell:
```sh
gradlew.bat run
```

- Mac/Linux:

```sh
./gradlew run
```

It may take a minute for a initial setup, once it's done all subsequent builds will be faster.

When the console says "75% executing", the server is ready up, for some reason it won't show "100%".

All dependencies we are using can be found in /app/build.gradle :+1:

## Accessing Webpage
Open your favourite web browser, type in the address bar:
```url
localhost:12345
```

## File Structure

**classpath: /app/src/main/**

---
### Resources

| classpath:/ | What should be put here? |
| ----------- | ------------------------ |
| resources/dynamic | HTML page |
| resources/static | CSS and JS |
| resources/static/persistence | Server configurations |
| resources/image | Rest of the resources for design, icon etc. | 

---
### Package

**root directory: classpath:/java/**

| Package name | Descriptions |
| ------------ | ------------ |
| cs261-project | Main server codes |
| cs261-project.config | Server configurations |
| cs261-project.dialect | Codes for porting SQLite to Spring |
| cs261-project.data_structure | Data structure for database instances |