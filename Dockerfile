# Use official Java 17 image
FROM openjdk:17

# Set working directory inside container
WORKDIR /app

# Copy source files into the container
COPY . .

# Compile Java source files to 'classes' directory
RUN mkdir classes && \
    find . -name "*.java" -print0 | xargs -0 javac -d classes

# Set default command to run the app
# Replace `controller.Main` with the fully qualified class containing your main method
CMD ["java", "-cp", "classes", "controller.Main"]
