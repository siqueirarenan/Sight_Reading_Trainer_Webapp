FROM adoptopenjdk:11-hotspot-bionic
RUN apt-get update
RUN apt-get -y install xorg gtk2-engines libasound2 libgtk2.0-0

# Add metadata to the image to describe which port the container is listening on at runtime.

# Run the specified command within the container.
CMD mvn jpro:run
