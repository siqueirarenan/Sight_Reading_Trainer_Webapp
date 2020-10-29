# Sight Reading Trainer (Webapp version) - by Renan Siqueira using JPro-Maven-Docker-Heroku

[![Build Status](https://travis-ci.com/JPro-one/HelloJPro-Maven.svg?branch=master)](https://travis-ci.com/JPro-one/HelloJPro-Maven)

This project is a webapp version of the Java programan Sight Reading Trainer. To keep the app independent from specific browser's plug-in, this app was deplying using JPro, a virtual browser capable of running Java, including Javafx. The app was built using JPro through Maven, put into a Docker container and shipped to the following page, where it can be seen:

[Sight Reading Trainer Webapp](https://sight-reading-trainer-webapp.herokuapp.com/)

The use of the app is easy. For more information about it, please access the [original repository](https://github.com/siqueirarenan/Sight_Reading_Trainer).

More about JPro: 
Website: [jpro.one](https://www.jpro.one/)
Twitter: [@jpro_one](https://twitter.com/jpro_one)

# Deployment of the app #

### Step 1 - Running it locally for testing ###

The app can be constructed using the template provided [here](https://github.com/JPro-one/HelloJPro-Maven). Special attention must be given to the location of resources (for example, in this step I had to specify the complete path of external pictures, while in the container a different approach had to be used, as it will be seen).

All the app dependencies are expressed in the 'pom.xml' file, including the JFugue library, used to deal with the musical notation of the app. Since the program runs Java 15 and JFugue is an unnamed older lybrary, the whole program was keept unnamed to avoid comflicts.

The app can be locally set by the command bellow.

```
mvn jpro:run
```

### Step2 - Build preparation and containering ###

After testing, the app must be realeased by the following command:

```
mvn jpro:release
```

A zip file containing the app will be created in the target-folder. This file must be extracted and two points were done here before proceeding:
- The Dockfile was added to this new folder. A Java 15 JDK was used to create the JPro environment. Alternatively, we could have [run Maven inside Docker](https://codefresh.io/howtos/using-docker-maven-maven-docker/), skipping the previous `jpro:release` step. Another Dockerfile is present in the root of this repository for this alternative, although it hasn't been tested.
- A folder with external resources was also added. This is the point where the path of these resources had to be altered in the programm.

Following, the app can be containered and tested again locally, with the commands bellow:

```
docker build -t renansiqueira/sight_reading_trainer_webapp .
docker run --publish 8000:8080 --detach --name bb renansiqueira/sight_reading_trainer_webapp
```

The app is again to be found at `localhost:8000`.

### Step 3 - Deployment to Heroku ###

Heroku server can be accessed through the following commands. Heroku app name can be changed:

```
heroku container:login
heroku create
```

Two things must be done before uploding to the Heroku server:
- in the 'bin/start.sh' file, the port parameter in the JPRO_ARGS must be changed $PORT (Heroku port variable)
- the following tag must be added to the container

```
docker tag renansiqueira/sight_reading_trainer_webapp registry.heroku.com/sight-reading-trainer-webapp/web
```

Finally, the container can be pushed to the server and released:

```
docker push registry.heroku.com/sight-reading-trainer-webapp/web
heroku container:release web --app sight-reading-trainer-webapp
heroku open --app sight-reading-trainer-webapp
````





