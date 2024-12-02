PlanetZ is an application designed to help users monitor their daily carbon emissions.

## Project Structure
/app/src/main/res: Contains xml files, strings, colors and drawables.
/app/src/test: Contains JUnit tests for unit testing.
/app/src/main/java/com/example/planetz : Contains the main codes for functionality of the app. As shown below (categorized by features):


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
  - **EcoBalance** : 


6. **Feature: EcoHub**

Key Classes for each package:



## Testing
### Unit Testing
- Location: /app/src/test
- Coverage for `LoginActivity` using mocked user inputs.

## Firebase Data Strucutre

1. **Feature: Login and Registration**

2. **Feature: Annual Carbon Footprint**


3. **Feature: EcoTracker**

habitTrackerList:

<img width="492" alt="Screenshot 2024-12-02 at 12 57 35" src="https://github.com/user-attachments/assets/51a037f3-94ca-4e2e-8a4b-6aedf68e444e">


4. **Feature: EcoGaugh**

emissions:

<img width="578" alt="Screenshot 2024-12-02 at 12 42 52" src="https://github.com/user-attachments/assets/1449c017-54a6-44f5-a33d-65b0b7eee964">


5. **Feature: EcoBalance**


emissions



## Future Modification



