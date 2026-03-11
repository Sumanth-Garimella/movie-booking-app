# Movie Booking Application

A Spring Boot-based movie booking application that allows users to register, authenticate, manage movies, and book tickets. The application integrates with MongoDB for data persistence, Kafka for messaging, and includes monitoring with Prometheus and Grafana.

## Features

- **User Management**: User registration, login, and password reset functionality
- **Movie Management**: Add, search, and delete movies
- **Ticket Booking**: Book tickets for movies with seat selection
- **Email Notifications**: Send registration confirmation emails
- **Real-time Messaging**: Kafka integration for ticket booking events
- **Monitoring**: Prometheus metrics and Grafana dashboards
- **Logging**: Centralized logging with Logstash and Elasticsearch
- **Security**: Spring Security for authentication and authorization

## Technologies Used

- **Backend**: Spring Boot 2.7.12
- **Database**: MongoDB
- **Messaging**: Apache Kafka
- **Security**: Spring Security
- **Email**: JavaMail API
- **Monitoring**: Prometheus, Grafana
- **Logging**: Logstash, Elasticsearch
- **Containerization**: Docker, Docker Compose
- **Build Tool**: Maven
- **Language**: Java 11

## Prerequisites

- Java 11 or higher
- Maven 3.6+
- Docker and Docker Compose
- Git

## Installation and Setup

1. **Clone the repository**:
   ```bash
   git clone <repository-url>
   cd movie-booking-app
   ```

2. **Configure Email Settings**:
   Update the email configuration in `src/main/resources/application.properties`:
   ```properties
   spring.mail.username=your-email@gmail.com
   spring.mail.password=your-app-password
   ```

3. **Build the application**:
   ```bash
   mvn clean install
   ```

## Running the Application

### Using Docker Compose (Recommended)

1. **Start all services**:
   ```bash
   docker-compose up -d
   ```

   This will start:
   - MongoDB (port 27017)
   - Kafka and Zookeeper (ports 9092, 2181)
   - Prometheus (port 9090)
   - Grafana (port 3000)
   - Elasticsearch (ports 9200, 9300)
   - Logstash (port 5044)
   - The Spring Boot application (port 8080)

2. **Stop services**:
   ```bash
   docker-compose down
   ```

### Running Locally

1. **Start dependencies** (MongoDB, Kafka, etc.) using Docker Compose**:
   ```bash
   docker-compose up mongo kafka zookeeper prometheus elasticsearch logstash -d
   ```

2. **Run the application**:
   ```bash
   mvn spring-boot:run
   ```

The application will be available at `http://localhost:8080`

## API Endpoints

### Admin/User Management
- `POST /api/v1.0/moviebooking/register` - Register a new user
- `GET /api/v1.0/moviebooking/login` - User login
- `GET /api/v1.0/moviebooking/{loginId}/forgot` - Forgot password
- `POST /api/v1.0/moviebooking/reset-password` - Reset password

### Movie Management
- `POST /api/v1.0/moviebooking/add` - Add a new movie
- `GET /api/v1.0/moviebooking/all` - Get all movies
- `GET /api/v1.0/moviebooking/movies/search/{movieName}` - Search movies by name
- `DELETE /api/v1.0/moviebooking/delete/{movieName}` - Delete a movie

### Ticket Booking
- `POST /api/v1.0/moviebooking/{movieName}/booking` - Book tickets for a movie
- `GET /api/v1.0/moviebooking/getallbookedtickets` - Get all booked tickets

## Configuration

### Application Properties
Key configuration settings in `application.properties`:

- **Server**: `server.port=8080`
- **MongoDB**: `spring.data.mongodb.uri=mongodb://localhost:27017/MovieBookingAPP`
- **Kafka**: `kafka.bootstrap-servers=localhost:9092`
- **Email**: SMTP configuration for Gmail
- **Logging**: File and Logstash configuration
- **Actuator**: Metrics and health endpoints

### Docker Compose Services
- **mongo**: MongoDB database
- **kafka**: Message broker
- **zookeeper**: Kafka dependency
- **prometheus**: Metrics collection
- **grafana**: Visualization dashboard
- **elasticsearch**: Log storage
- **logstash**: Log processing
- **backend**: Spring Boot application

## Monitoring

### Prometheus
Access Prometheus at `http://localhost:9090`
- Scrapes metrics from the Spring Boot actuator endpoint

### Grafana
Access Grafana at `http://localhost:3000` (default credentials: admin/admin)
- Create dashboards to visualize application metrics

### Elasticsearch
Access Elasticsearch at `http://localhost:9200`
- Stores processed logs from Logstash

## Testing

Run tests using Maven:
```bash
mvn test
```

## Project Structure

```
movie-booking-app/
├── src/
│   ├── main/
│   │   ├── java/com/fse/moviebookingapp/
│   │   │   ├── controller/     # REST controllers
│   │   │   ├── dto/           # Data transfer objects
│   │   │   ├── model/         # Entity models
│   │   │   ├── repository/    # Data repositories
│   │   │   ├── service/       # Business logic
│   │   │   ├── config/        # Configuration classes
│   │   │   ├── exception/     # Exception handlers
│   │   │   └── util/          # Utility classes
│   │   └── resources/         # Application properties
│   └── test/                  # Unit tests
├── Dockerfile                 # Docker image definition
├── docker-compose.yml         # Multi-container setup
├── pom.xml                    # Maven configuration
├── prometheus.yml             # Prometheus configuration
├── logstash.conf              # Logstash pipeline
└── README.md                  # This file
```

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if necessary
5. Submit a pull request

## License

This project is licensed under the MIT License.</content>
<parameter name="filePath">/Users/a1989/IdeaProjects/movie-booking-app/README.md
