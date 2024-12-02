PlanetZ is designed to promote sustainability and help users reduce their carbon footprint. It includes features such as tracking emissions, visualizing progress, offsetting carbon, and providing educational resources.

## Instructions for Use
  - **LoginandRegister**: Create an account or log in using your email and password. New users can sign up easily and start tracking their carbon footprint.
  - **Complete the Initial Questionnaire**：Fill out a quick questionnaire about your energy use, transportation, diet, and shopping habits. This calculates your Annual Carbon Footprint, giving you a baseline to track and reduce your emissions.
  - **Track Emissions (Eco Tracker)**: Enter details about your daily activities, like commuting, energy use, and diet, in a quick questionnaire. The Eco Tracker calculates your carbon footprint, giving insights into areas for improvement.
  - **Visualize Progress (Eco Gauge)**: View your progress toward reducing emissions with clear visuals like graphs and charts. The Eco Gauge motivates you by showing how your actions make a difference.
  - **Offset Emissions (Eco Balance)**: Explore tailored recommendations to offset your carbon impact, such as supporting green projects, planting trees, or purchasing carbon credits.
  - **Learn and Engage (Eco Hub)**：Discover sustainability tips, educational content, and eco-friendly trends. Use this resource center to stay informed and make greener lifestyle choices.

## Project Structure
/app/src/main/res: Contains xml files, strings, colors and drawables.
/app/src/test: Contains JUnit tests for unit testing.
/app/src/main/java/com/example/planetz : Contains the main codes for functionality of the app. As shown below (categorized by features):

# **Guide for Developers: Maintaining and Developing Eco App**

## **1. Codebase Organization**
- **Feature-Based Structure**:  
  Group code into clearly defined modules corresponding to the app’s main features:  
  - **EcoTracker**: Handles carbon footprint calculation logic and user data inputs.  
  - **EcoGauge**: Manages visualization and graphical representation of progress.  
  - **EcoBalance**: Contains offset recommendation logic and integration with external resources.  
  - **EcoHub**: Stores and renders educational resources and tips.  
- **Separation of Concerns**:  
  - Keep UI logic in Activities/Fragments.  
  - Store business logic in ViewModels and Repositories.  
  - Use utility classes for reusable methods.

## **2. Development Practices**
- **Follow SOLID Principles**:  
  - Apply modularity in each feature module (e.g., EcoTracker should not directly depend on EcoGauge logic).  
  - Ensure each class has a single responsibility, such as calculating emissions or rendering graphs.  
- **Git Workflow**:  
  - Use feature branches for adding new functionality (e.g., `feature/eco-gauge-ui`).  
  - Create detailed pull requests with screenshots or test results when merging changes.  
  - Maintain a `CHANGELOG.md` to log updates, bug fixes, and enhancements.  

## **3. Dependencies and Library Management**
- **Firebase Integration**:  
  - Ensure all Firebase dependencies (Auth, Firestore, Analytics) are up-to-date and compatible with the app’s functionality.  
  - Use Firestore indexes and real-time listeners efficiently to reduce database costs.  
- **MPAndroidChart**:  
  - Optimize graph rendering for the EcoGauge module by customizing animations and datasets.  
- **Glide**:  
  - Use this library to manage image loading in EcoHub for seamless educational content display.  

## **4. Testing**
- **Unit Testing**:  
  - Test carbon footprint calculations in **EcoTracker** to ensure accuracy.  
  - Use mock Firebase instances to test database interactions.  
- **UI Testing**:  
  - Automate navigation tests for EcoGauge and EcoHub using Espresso.  
  - Verify the responsiveness of questionnaire layouts on various device sizes.  
- **Integration Testing**:  
  - Validate the flow between modules (e.g., how data from EcoTracker updates the EcoGauge).

## **5. Maintenance Tips**
- **Data Privacy Compliance**:  
  - Always anonymize user data before analytics or external integration.  
  - Periodically review Firebase rules to ensure user data security.  
- **Optimize Performance**:  
  - Use lazy-loading techniques in the EcoHub to improve app load time.  
  - Periodically review app memory usage, especially in graph-heavy features like EcoGauge.

## **6. Future Development**
- **AI-Powered Suggestions**:  
  - Integrate machine learning models to analyze user habits and provide personalized emission reduction tips in EcoTracker.  
- **User Engagement**:  
  - Expand EcoHub to include a forum for users to share tips and progress.  
  - Gamify progress tracking with badges and milestones in EcoGauge.  
- **Scalability**:  
  - Monitor Firestore usage and adjust indexes as the user base grows.

## **7. Collaboration and Resources**
- **GitHub Repository**:  
  Centralize all development efforts in a private GitHub repository.  
  - Include detailed issue tracking and labels (e.g., `bug`, `enhancement`, `UI`).  
- **Jira for Task Management**:  
  Use Jira to track development sprints, milestones, and bug fixes.  

## **8. Key Setup for New Developers**
- **Development Environment**:  
  - Install Android Studio and configure the app's dependencies using the `build.gradle` file.  
  - Add the Firebase configuration file (`google-services.json`) to the appropriate folder.  
- **Quick Start**:  
  - Run the app on an emulator or device to explore functionality.  
  - Review documentation in the `README.md` file for module details and development standards.  

## **Overall Structure**

The Eco App is structured into three main layers: Frontend, Backend, and Integration. Each layer has specific responsibilities, ensuring modularity, scalability, and maintainability.

---

### **1. Frontend**
The frontend handles user interaction and provides a visually appealing interface for tracking, visualizing, and reducing carbon emissions.

- **Technology**: Android (Java and XML)
- **Components**:
  - **Interactive UI**: Users complete questionnaires, view graphs, and access sustainability resources.
  - **View Binding**: Simplifies binding UI components to business logic.
  - **Modules**:
    - **EcoTracker**: Captures user inputs related to daily activities.
    - **EcoGauge**: Visualizes progress through interactive charts and graphs.
    - **EcoBalance**: Provides actionable recommendations for offsetting emissions.
    - **EcoHub**: Displays curated sustainability tips, educational content, and videos.

---

### **2. Backend**
The backend is the core engine that processes user data, performs calculations, and ensures secure storage and retrieval. It is built using Firebase’s suite of cloud tools, making it robust and scalable.

- **Technology**: Firebase Firestore, Firebase Auth, Firebase Cloud Functions
- **Responsibilities**:
  - **Data Storage**: Firebase Firestore is used to store user data, such as activity logs, questionnaire responses, carbon footprint results, and progress metrics.
  - **Authentication**: Firebase Auth handles secure user login and account management, supporting email/password authentication.
  - **Real-Time Updates**: Firestore’s real-time database ensures users see up-to-date progress and educational content instantly.
  - **Business Logic**:
    - Carbon footprint calculation algorithms based on user inputs.
    - Mapping activities (e.g., diet, transportation) to emission factors using pre-defined datasets.
    - Storing user preferences and habits for generating personalized recommendations.
  - **Cloud Functions**:
    - Automated triggers for specific events, like recalculating carbon footprint when new data is submitted.
    - Serving curated content dynamically based on user interest (e.g., EcoHub articles).
  - **Data Analytics**:
    - Use Firebase Analytics to monitor user engagement and identify improvement opportunities.
    - Analyze aggregated data to optimize emission factors and resource recommendations.

- **Modules**:
  1. **Data Management**:
     - Manages CRUD (Create, Read, Update, Delete) operations for all user-related information.
  2. **Calculation Logic**:
     - Processes raw user inputs to compute annual carbon footprints.
     - Maps complex activity inputs (e.g., flight frequency) into meaningful emission data.
  3. **Content Delivery**:
     - Handles dynamic content updates for EcoHub based on Firebase queries.
  4. **Security and Privacy**:
     - Configures Firestore rules to ensure user data remains secure and accessible only by authorized accounts.
     - Implements data anonymization for aggregated analytics.

---

### **3. Integration**
The integration layer ensures seamless communication between the frontend and backend.

- **Technology**: Firebase SDK
- **Responsibilities**:
  - Synchronizing user input and calculated results in real-time between the app and Firestore.
  - Handling API calls for advanced operations, such as fetching recommended actions for EcoBalance.
  - Managing secure data transfer and complying with privacy standards.


### **How It All Comes Together**
1. **User Journey**:
   - Users interact with the **frontend** to input data and view results.
   - The **backend** processes inputs, calculates carbon footprints, and stores data securely.
   - Real-time synchronization ensures the **frontend** reflects the latest updates.

2. **Modularity**:
   - Each feature (EcoTracker, EcoGauge, EcoBalance, EcoHub) operates as an independent module, ensuring ease of maintenance and upgrades.

3. **Scalability**:
   - Firebase’s cloud-based infrastructure allows the app to scale effortlessly to support a growing user base while maintaining performance.

4. **Performance Optimization**:
   - Firestore indexing and efficient queries ensure quick data retrieval.
   - Cloud Functions reduce frontend processing load by offloading complex calculations to the backend.

---

# **Key Components**

This detailed structure highlights how the Eco App leverages a robust backend to deliver a seamless user experience while maintaining flexibility for future enhancements.

---

## **Feature: Login and Registration**
### **Packages**:
- **LoginandRegister**:  
  Responsible for user logins, registration, and password reset functionalities.

### **Key Classes**:
- `LoginActivity`
- `RegisterActivity`
- `ForgetPassword`

---

## **Feature: Annual Carbon Footprint**
### **Packages**:
- **Questions**:  
  Gathers user data about carbon emissions through a questionnaire and updates all inputs to Firebase.  
- **Calculator**:  
  Calculates carbon emissions in different categories (consumption, food, housing, and transportation) and provides total and category-wise results.  
- **Displaying Annual Footprint Result**:  
  Displays the total and category-specific annual carbon footprint in a clear format.

### **Key Classes**:
- **Questions**: All classes in the package.
- **Calculator**: `BigResult`
- **Displaying Annual Footprint Result**: `ResultActivity`

---

## **Feature: EcoTracker**
### **Packages**:
- **HabitSuggestionandTracker**:  
  - Provides personalized habit suggestions based on questionnaire responses.  
  - Tracks user progress in adopting sustainable habits.  
  - Allows users to search habits by keywords and filter them by category or environmental impact.  
  - Sends reminders to users to log activities.

### **Key Classes**:
- `HabitRecommendation`
- `TrackingHabit`
- `SearchingKeyword`

---

## **Feature: EcoGauge**
### **Packages**:
- **Data**:  
  Stores data from the EcoTracker and retrieves local data from Firebase.  
- **EcoGauge**:  
  - Displays total CO2 emissions and a breakdown by categories.  
  - Shows emission trends over time and comparisons with global averages.  
- **EcoGaugeUI**:  
  XML-based UI for EcoGauge functionalities.

### **Key Classes**:
- `EcoGaugeMainActivity`: Main dashboard for EcoGauge.
- `EmissionDashboard`
- `EmissionUtils`

---

## **Feature: EcoBalance**
### **Packages**:
- **EcoBalance**:  
  Displays information about offset projects and allows users to select and pay for them.

### **Key Classes**:
- `EcoBalanceHomePageActivity`
- `EcoBalancePay`
- `ProjectDetailActivity`
- `ProjectActivity`

---

## **Feature: EcoHub**
### **Packages**:
- **EcoHub**:  
  A resource center providing learning materials, market trends, and recommendations for sustainable products to promote eco-friendly practices and informed decisions.

### **Key Classes**:
- `EcoHubActivity`


# **Dependencies and Why They're Needed**

## **Firebase Platform and Dependencies**
1. **`platform("com.google.firebase:firebase-bom:33.6.0")`**  
   Manages the versions of all Firebase libraries, ensuring compatibility across Analytics, Database, Auth, and Firestore.  

2. **`com.google.firebase:firebase-analytics`**  
   Tracks user behavior and engagement, providing insights to improve app performance and user experience.  

3. **`com.google.firebase:firebase-database`**  
   Enables real-time database functionality, storing user activity logs and syncing data efficiently across devices.  

4. **`com.google.firebase:firebase-auth`**  
   Provides secure user authentication for login and registration using email and password.  

5. **`com.google.firebase:firebase-firestore`**  
   A NoSQL cloud database that stores structured user data, including habits, preferences, and activity progress, with real-time syncing capabilities.

---

## **AndroidX Libraries**
1. **`androidx.appcompat:appcompat:1.6.1`**  
   Ensures backward compatibility for modern Android features, allowing the app to run smoothly on older devices.  

2. **`androidx.core:core-ktx:1.12.0`**  
   Simplifies Android development by providing Kotlin extensions for common tasks.  

3. **`com.google.android.material:material:1.9.0`**  
   Supports Material Design components for a polished and modern user interface.  

4. **`androidx.constraintlayout:constraintlayout:2.1.4`**  
   Helps create complex UI layouts with better performance and flexibility.  

5. **`androidx.recyclerview:recyclerview:1.3.1`**  
   Displays dynamic lists of data, such as activities or recommended habits, with smooth scrolling and item animations.  

6. **`androidx.viewpager2:viewpager2:1.0.0`**  
   Manages swipe-based navigation between pages, ideal for displaying educational content in EcoHub.  

7. **`androidx.lifecycle:lifecycle-viewmodel` and `lifecycle-livedata`**  
   Manages UI-related data with lifecycle-aware components, ensuring consistent and efficient updates.  

8. **`androidx.activity:activity-ktx:1.7.2`**  
   Simplifies common Activity-related tasks with Kotlin-friendly APIs.  

---

## **Additional Libraries**
1. **`com.github.PhilJay:MPAndroidChart:3.1.0`**  
   Generates dynamic and visually appealing charts for EcoGauge to display CO2 emissions and trends.  

2. **`de.hdodenhof:circleimageview:3.1.0`**  
   Adds circular image view functionality, used for profile pictures or graphical elements in the UI.  

3. **`androidx.annotation:annotation:1.6.0`**  
   Provides compile-time checks to improve code reliability and maintainability.

---

## **Testing Libraries**
1. **`junit:junit:4.13.2`**  
   Provides a framework for writing and running unit tests to ensure reliable code behavior.  

2. **`androidx.test.ext:junit:1.1.5`**  
   Extends JUnit functionality for Android, enabling UI and instrumentation testing.  

3. **`androidx.test.espresso:espresso-core:3.5.1`**  
   Facilitates automated UI testing by simulating user interactions.  

---

## **Plugins**
1. **`com.android.application`**  
   Configures the project as an Android application, managing builds, packaging, and APK generation.  

2. **`com.google.gms.google-services`**  
   Connects the app to Firebase services, enabling Analytics, Auth, and Database functionality.

---

By integrating these dependencies, the Eco App ensures seamless functionality, robust backend operations, and a user-friendly interface while enabling efficient testing and development workflows.

# **Assumptions**

The Eco App was developed based on these key assumptions:

---

## **User Behavior**
1. Users can provide accurate and honest data about their activities.  
2. Users are motivated to track and reduce their carbon footprint.  

## **Carbon Footprint Calculation**
1. Emission factors used are globally accepted averages.  
2. Calculations focus only on user-controlled emissions like transportation and energy use.  

## **Technological Assumptions**
1. Users have stable internet access for real-time syncing.  
2. The app supports devices running Android 7.0 (API level 24) and above.  

## **Features and Engagement**
1. Users will actively engage with features like EcoTracker and EcoGauge.  
2. Educational content in EcoHub will drive informed sustainability decisions.

# **Links**

## **GitHub Repository**
The source code for the Eco App is hosted on GitHub. Developers can access the repository for version control, issue tracking, and collaboration:  
[GitHub Repository](https://github.com/beta-gao/PlanetZ)

---

## **Jira Board**
Task management and project tracking are managed on Jira. Team members can view sprints, assign tasks, and monitor progress:  
[Jira Board](https://gonggequan.atlassian.net/jira/software/projects/B07PROJ/boards/2)


# **Existing Credentials**

Below are the existing credentials to sign in to the Eco App for testing and development purposes:

---

## **Admin Account**
- **Email**: `yiqin.gao@mail.utoronto.ca`  
- **Password**: `20050711`

---

## **Test User Account**
- **Email**: `3371593089@qq.com`  
- **Password**: `20050711`


1. **Feature: Annual Carbon Footprint**

annualCarbonFootprint:

![3841733168848_ pic](https://github.com/user-attachments/assets/7b062a3e-7ae0-48ff-a018-d82ef9b95372)


2. **Feature: EcoTracker**

habitTrackerList:

<img width="492" alt="Screenshot 2024-12-02 at 12 57 35" src="https://github.com/user-attachments/assets/51a037f3-94ca-4e2e-8a4b-6aedf68e444e">


3. **Feature: EcoGaugh**

emissions:

<img width="578" alt="Screenshot 2024-12-02 at 12 42 52" src="https://github.com/user-attachments/assets/1449c017-54a6-44f5-a33d-65b0b7eee964">



## Future Modification



