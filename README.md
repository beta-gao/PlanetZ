# **PlanetZ Documentation**

PlanetZ is designed to promote sustainability and help users reduce their carbon footprint. It includes features such as tracking emissions, visualizing progress, offsetting carbon, and providing educational resources.

Our team has relatively few pull requests primarily because we frequently conduct offline meetings to merge our individual branches. This collaborative approach allows us to resolve conflicts and integrate code in real-time, reducing the need for frequent online pull requests.

---
# **Links**

## **GitHub Repository**
[GitHub Repository](https://github.com/beta-gao/PlanetZ)

---

## **Jira Board**
[Jira Board](https://gonggequan.atlassian.net/jira/software/projects/B07PROJ/boards/2)

---

# **Existing Credentials**

- **Admin Account**:  
  - Email: `yiqin.gao@mail.utoronto.ca`  
  - Password: `20050711`

- **Test User Account**:  
  - Email: `3371593089@qq.com`  
  - Password: `20050711`

---

## **Instructions for Use**

- **LoginandRegister**:  
  Create an account or log in using your email and password. New users can sign up easily and start tracking their carbon footprint.

- **Complete the Initial Questionnaire**:  
  Fill out a quick questionnaire about your energy use, transportation, diet, and shopping habits. This calculates your Annual Carbon Footprint, giving you a baseline to track and reduce your emissions.

- **Track Emissions (Eco Tracker)**:  
  Enter details about your daily activities, like commuting, energy use, and diet, in a quick questionnaire. The Eco Tracker calculates your carbon footprint, giving insights into areas for improvement.

- **Visualize Progress (Eco Gauge)**:  
  View your progress toward reducing emissions with clear visuals like graphs and charts. The Eco Gauge motivates you by showing how your actions make a difference.

- **Offset Emissions (Eco Balance)**:  
  Explore tailored recommendations to offset your carbon impact, such as supporting green projects, planting trees, or purchasing carbon credits.

- **Learn and Engage (Eco Hub)**:  
  Discover sustainability tips, educational content, and eco-friendly trends. Use this resource center to stay informed and make greener lifestyle choices.

---

## **Project Structure**

- **/app/src/main/res**: Contains XML files, strings, colors, and drawables.
- **/app/src/test**: Contains JUnit tests for unit testing.
- **/app/src/main/java/com/example/planetz**: Contains the main code for app functionality, categorized by features.

---

# **Guide for Developers**

## **1. Codebase Organization**

- **Feature-Based Structure**:
  - **EcoTracker**: Handles carbon footprint calculation logic and user data inputs.
  - **EcoGauge**: Manages visualization and graphical representation of progress.
  - **EcoBalance**: Contains offset recommendation logic and integration with external resources.
  - **EcoHub**: Stores and renders educational resources and tips.

- **Separation of Concerns**:  
  - UI logic is kept in Activities/Fragments.  
  - Business logic resides in ViewModels and Repositories.  
  - Utility classes are used for reusable methods.

---

## **2. Development Practices**

- **Follow SOLID Principles**:  
  Apply modularity and ensure each feature module is independent. Each class should have a single responsibility.

- **Git Workflow**:  
  Use feature branches for updates, detailed pull requests, and maintain a `CHANGELOG.md`.

---

## **3. Dependencies and Library Management**

- **Firebase Integration**:  
  Keep dependencies up-to-date for real-time data, authentication, and analytics.
- **MPAndroidChart**:  
  Optimize graph rendering for EcoGauge.
- **Glide**:  
  Efficiently handle image loading for EcoHub.

---

## **4. Testing**

- **Unit Testing**: Test carbon footprint calculations in EcoTracker.
- **UI Testing**: Automate navigation tests for EcoGauge and EcoHub using Espresso.

---

## **5. Future Development**

- Integrate AI-powered suggestions for emission reductions.
- Expand EcoHub to include a community forum.
- Gamify progress tracking with badges and milestones.

---

## **Overall Structure**

- **Frontend**:  
  Android (Java and XML) for user interaction.
- **Backend**:  
  Firebase Firestore for storage, authentication, and real-time updates.
- **Integration**:  
  Synchronization between frontend and backend using Firebase SDK.

---

# **Key Components**

### **Feature: Login and Registration**
- **Packages**: LoginandRegister  
- **Key Classes**:  
  - `LoginActivity`, `RegisterActivity`, `ForgetPassword`

---

### **Feature: Annual Carbon Footprint**
- **Packages**: Questions, Calculator, Displaying Annual Footprint Result  
- **Key Classes**:  
  - `BigResult`, `ResultActivity`

---

### **Feature: EcoTracker**
- **Packages**: HabitSuggestionandTracker  
- **Key Classes**:  
  - `HabitRecommendation`, `TrackingHabit`, `SearchingKeyword`

---

### **Feature: EcoGauge**
- **Packages**: Data, EcoGauge, EcoGaugeUI  
- **Key Classes**:  
  - `EcoGaugeMainActivity`, `EmissionDashboard`, `EmissionUtils`

---

### **Feature: EcoBalance**
- **Packages**: EcoBalance  
- **Key Classes**:  
  - `EcoBalanceHomePageActivity`, `EcoBalancePay`, `ProjectDetailActivity`

---

### **Feature: EcoHub**
- **Packages**: EcoHub  
- **Key Classes**:  
  - `EcoHubActivity`

---

# **Dependencies and Why They're Needed**

- **Firebase Platform**:  
  Provides backend services, including real-time updates, authentication, and cloud storage.
- **AndroidX Libraries**:  
  Simplifies development and ensures backward compatibility.
- **Additional Libraries**:  
  - **MPAndroidChart**: Visualizes data for EcoGauge.
  - **Glide**: Efficiently handles image rendering.

---

# **Assumptions**

- Users are motivated to reduce their carbon footprint.  
- Data provided by users is accurate and honest.  
- Devices have stable internet access and run Android 7.0 or above.

---

# **Feature Images**

### **Annual Carbon Footprint**
![Annual Carbon Footprint](https://github.com/user-attachments/assets/7b062a3e-7ae0-48ff-a018-d82ef9b95372)

---

### **EcoTracker**
![Habit Tracker List](https://github.com/user-attachments/assets/51a037f3-94ca-4e2e-8a4b-6aedf68e444e)

---

### **EcoGauge**
![Emissions Chart](https://github.com/user-attachments/assets/1449c017-54a6-44f5-a33d-65b0b7eee964)

---

# **Future Modifications**

- Enhance the EcoHub with community-driven content.
- Add advanced gamification features to increase engagement.
- Scale Firebase Firestore for larger user bases.

---
