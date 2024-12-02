PlanetZ is an application designed to help users monitor their daily carbon emissions.

## Project Structure
/app/src/main/res: Contains xml files, strings, colors and drawables.
/app/src/test: Contains JUnit tests for unit testing.
/app/src/main/java/com/example/planetz : Contains the main codes for functionality of the app. As shown below (categorized by features):


**Feature: Login and Registration**

Packages:
  - **LoginandRegister**: Responsible for user logins, registration and reset password.


**Feature: Annual Carbon Footprint**

Packages:
  - **Questions** : Ask users questions to obtain user's carbon emission level
  - **Calculator**: calculate consumption,food, housing and transportation part of annual footprint separately
  - **Displaying Annual Footprint Result** : calculate the total annual footprint

**Feature: EcoTracker**


Packages:
  - **HabitSuggestionandTracker**: suggest personalized habits(based on questions), habit tracker(track progress), search habits by keywords and filters(category and impact,
                                   remind users to log activites.


**Feature: EcoGaugh**

Packages:
  - **Data**: store data from Ecotracker, and obtain data from firebase locally.
  - **EcoGaugh**: Using data collected from habit tracker, reponsible for display total CO2 emission, CO2 emission breakdown by category, emission trend graph, display                    comparison with global average.
  - **EcoGaughui**: Combined with xml for UI
Class:
 - **EcoGaugeMainAcitivity**: main dashboard of ecoGauge


**Feature: EcoBalance**

Packages:
  - **EcoBalance** :


**Feature: EcoHub**

Packages:


## Key Components
**Feature: Login and Registration**

Key Classes for each package:
  - **LoginandRegister**: LoginActivity, RegisterAcitivity, ForgetPassword.

**Feature: Annual Carbon Footprint**

Key Classes for each package:
  - **Questions** : all classes
  - **Calculator**: BigResult
  - **Displaying Annual Footprint Result** : ResultActivity

**Feature: EcoTracker**

Key Classes for each package:
  - **Data**:
  - **HabitSuggestionandTracker**: HabitRecommmendation, TrackingHabit, SearchingKeyword


**Feature: EcoGaugh**

Key Classes for each package:
  - **EcoGaugh**: EmissionDashboard, EmissionUtils,


**Feature: EcoBalance**

Key Classes for each package:
  - **EcoBalance** : 


**Feature: EcoHub**

Key Classes for each package:

## Testing
### Unit Testing
- Location: /app/src/test
- Coverage for `LoginActivity` using mocked user inputs.


