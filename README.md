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

This detailed structure highlights how the Eco App leverages a robust backend to deliver a seamless user experience while maintaining flexibility for future enhancements.


1. **Feature: Login and Registration**

Packages:
  - **LoginandRegister**: Responsible for user logins, registration and reset password.


2. **Feature: Annual Carbon Footprint**

Packages:
  - **Questions** : Ask users questions to obtain user's carbon emission level, update all message to fire base
  - **Calculator**: calculate consumption,food, housing and transportation part of annual footprint separately and totally
  - **Displaying Annual Footprint Result** : diaplay the total and seperate annual footprint

3. **Feature: EcoTracker**


Packages:
  - **HabitSuggestionandTracker**: suggest personalized habits(based on questions), habit tracker(track progress), search habits by keywords and filters(category and impact,
                                   remind users to log activites.


4. **Feature: EcoGaugh**

Packages:
  - **Data**: store data from Ecotracker, and obtain data from firebase locally.
  - **EcoGaugh**: Using data collected from habit tracker, reponsible for display total CO2 emission, CO2 emission breakdown by category, emission trend graph, display                    comparison with global average.
  - **EcoGaughui**: Combined with xml for UI

Class:
 - **EcoGaugeMainAcitivity**: main dashboard of ecoGauge


5. **Feature: EcoBalance**

Packages:
  - **EcoBalance** : Display information about items available for users to purchase, and provide user selection and payment functions.


6. **Feature: EcoHub**

Packages: 
  - **EcoHub** : EcoHub offers learning resources, market trends, and sustainable product recommendations to promote eco-friendly practices and informed decisions.


## Key Components
1. **Feature: Login and Registration**

Key Classes for each package:
  - **LoginandRegister**: LoginActivity, RegisterAcitivity, ForgetPassword.

2. **Feature: Annual Carbon Footprint**

Key Classes for each package:
  - **Questions** : all classes
  - **Calculator**: BigResult
  - **Displaying Annual Footprint Result** : ResultActivity

3. **Feature: EcoTracker**

Key Classes for each package:
  - **HabitSuggestionandTracker**: HabitRecommmendation, TrackingHabit, SearchingKeyword


4. **Feature: EcoGaugh**

Key Classes for each package:
  - **EcoGaugh**: EmissionDashboard, EmissionUtils,


5. **Feature: EcoBalance**

Key Classes for each package:
  - **EcoBalance** : EcoBalanceHomePageActivity, EcoBalancePay, ProjectDetailActivity, ProjectActivity


6. **Feature: EcoHub**

Key Classes for each package:
  - **EcoHub** : EcoHubActivity



## Testing
### Unit Testing
- Location: /app/src/test
- Coverage for `LoginActivity` using mocked user inputs.

## Firebase Data Strucutre


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



