# Plane Crashes Data Analysis Tool

## About The Project

This console-based application provides a comprehensive interface for analyzing and exploring historical passenger plane crashes data. The tool offers robust functionality for data manipulation, searching, sorting, and filtering of plane crash records, making it valuable for researchers, analysts, and aviation safety professionals.

### Key Features

* Flexible data listing with customizable field selection
* Advanced sorting capabilities for any data field
* Powerful search functionality with flexible string matching
* Comprehensive filtering system with multiple conditions
* Efficient field access using Reflection API
* Data export to CSV format
* Interactive menu system for sequential operations
* Data persistence and reusability across operations

### Built With

* Java
* Java Stream API
* Java Time API 
* Java Reflection API
* Collections Framework

## Getting Started

### Prerequisites

* Java Development Kit (JDK) 8 or higher
* Command line interface or terminal
* Git (for version control)

### Installation

1. Clone the repository
```sh
git clone [repository-url]
```

2. Compile the Java files
```sh
javac *.java
```

3. Run the program
```sh
java Main
```

## Usage

### 1. Listing Data
- View all fields for each crash record
- Select specific fields to display
- List entities within a specified row range
- Get total count of listed entries
- Export any list view to CSV with proper headers

### 2. Sorting Capabilities
- Sort by any available field using dynamic field access
- Choose ascending (ASC) or descending (DESC) order
- Maintain sorted results for subsequent operations
- Export sorted results to CSV format

### 3. Search Functionality
- Search by any field with intelligent field type detection
- String fields: Contains-based search
- Non-string fields: Exact match search
- Export search results to CSV with query information

### 4. Field Information
- Display all available field names
- Dynamic field type information
- Use for reference in other operations

### 5. Advanced Filtering
* String Fields:
  - Starts with
  - Ends with
  - Contains
  - Null check
* Numeric/Date Fields:
  - Equal to
  - Greater than
  - Less than
  - Greater or equal to
  - Less or equal to
  - Between
  - Null check
* Date-specific:
  - Year filter
  - Month filter
  - Day filter
* Export filtered results to CSV with filter criteria

## Development

### Code Structure
1. Modular design with specific task-focused methods
2. Comprehensive exception handling with user-friendly messages
3. Interactive and intuitive menu system
4. Results collection system for operation chaining
5. Reflection-based field access for improved maintainability

### Best Practices
- Clean code principles
- Proper error handling
- Extensive input validation
- Efficient data processing
- Regular testing

## Contributing

Contributions are welcome. Please feel free to submit pull requests.

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

Distributed under the MIT License. See `LICENSE` for more information.

## Contact

Project Link: [https://github.com/yourusername/plane-crashes-analysis](https://github.com/yourusername/plane-crashes-analysis)

## Acknowledgments

* Java development community
* Open-source aviation safety data providers
* Contributors and testers