# IoT Manager

The project's main goal was to design a Web interface for IoT network devices through Web Components, aiming at its use by users of technological framework management systems involving cyber-physical equipments. The graphical representation was also intended to be inserted as elements that allow users to interact with the devices and manipulate them by changing their attributes. 

<h1 align="center">
    <img alt="Home" title="Home" src="/iot-manager/blob/main/src/main/resources/META-INF/resources/images/home.png" width="820px" />
</h1>


## Running the Application

The project is a standard Maven project. To run it from the command line,
type `mvnw` (Windows), or `./mvnw` (Mac & Linux), then open
http://localhost:8080 in your browser.

You can also import the project to your IDE of choice as you would with any
Maven project. Read more on [how to import Vaadin projects to different 
IDEs](https://vaadin.com/docs/latest/flow/guide/step-by-step/importing) (Eclipse, IntelliJ IDEA, NetBeans, and VS Code).

## Deploying to Production

To create a production build, call `mvnw clean package -Pproduction` (Windows),
or `./mvnw clean package -Pproduction` (Mac & Linux).
This will build a JAR file with all the dependencies and front-end resources,
ready to be deployed. The file can be found in the `target` folder after the build completes.

Once the JAR file is built, you can run it using
`java -jar target/iotmanager-1.0-SNAPSHOT.jar`

## Project Structure

- `MainLayout.java` in `src/main/java` contains the navigation setup (i.e., the
  side/top bar and the main menu). This setup uses
  [App Layout](https://vaadin.com/components/vaadin-app-layout).
- `views` package in `src/main/java` contains the server-side Java views of your application.
- `views` folder in `frontend/` contains the client-side JavaScript views of your application.
- `themes` folder in `frontend/` contains the custom CSS styles.

## SQL

In the resources folder inside a sql package you can find a file named "UX-IOT Schema.sql" that includes all the necessary schemas to create your DB tables and all that you need to do is just to import it to a admnistration database program. For that we suggest DBeaver but you can use one of your preference. 

    # Install 
        [DBeaver](https://dbeaver.io/)
      
  
## Rest Server

To get all the information of your web component while manipulating his specifications you can clown our
REST Server from this repo:  [https://github.com/gustavodev1998/RestServerSimulator](https://github.com/gustavodev1998/RestServerSimulator).

This is a NodeJS server so to run you need to have the following installed:

    # Install 
        [NodeJS](https://nodejs.org/en/)
  
To run the program you need to insert the following command: 

```shell
# Node modules
    npm install
```

```shell
# Run node
    npm start
```

## Paper

If you wish to read how this project was developed and the process of construction you can go to our docs folder and read
the pdf that contains the paper of the UX-IOT 

## Useful links

- Read the Vaadin documentation at [vaadin.com/docs](https://vaadin.com/docs).
- Read the Lit documentation at [lit.dev/docs/](https://lit.dev/docs/).

## :european_post_office: Team

**LEIRT61D**
<br>
Developed by the students:

<ul>
  <li> Gustavo Campos A47576
  <li> Tiago Cebola A47594
</ul>
