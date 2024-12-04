package com.example.planetz.EcoHub;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.planetz.HomePageActivity;
import com.example.planetz.MainDashboard;
import com.example.planetz.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EcoHubActivity extends AppCompatActivity {

    private TextView learningResourceTextView;
    private TextView marketTrendTextView;
    private TextView ecoProductTextView;
    private Button learningResourceButton;

    private final List<String[]> learningResources = new ArrayList<>();
    private final List<String> marketTrends = new ArrayList<>();
    private final List<String> ecoProducts = new ArrayList<>();
    private final Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eco_hub);

        // Initialize views
        learningResourceTextView = findViewById(R.id.learningResourceTextView);
        marketTrendTextView = findViewById(R.id.marketTrendTextView);
        ecoProductTextView = findViewById(R.id.ecoProductTextView);
        learningResourceButton = findViewById(R.id.learningResourceButton);

        // Initialize static data
        initializeStaticData();

        // Randomly select and display content
        displayRandomContent();

        // Set click listener for the Learning Resource button
        learningResourceButton.setOnClickListener(v -> {
            // Open the URL of the selected learning resource
            String url = learningResources.get(random.nextInt(learningResources.size()))[1];
            openUrl(url);
        });

        // Enable back navigation to the homepage
        enableBackToHomepage();
    }

    private void initializeStaticData() {
        // Add learning resources (Title, URL)
        learningResources.add(new String[]{"The Future of Renewable Energy", "https://education.nationalgeographic.org/resource/renewable-energy/"});
        learningResources.add(new String[]{"The Future of Renewable Energy", "https://www.youtube.com/watch?v=UVf2Yw7uFoE"});
        learningResources.add(new String[]{"How Solar Panels Work", "https://www.energy.gov/eere/solar/how-does-solar-work"});
        learningResources.add(new String[]{"The Basics of Sustainable Living", "https://www.youtube.com/watch?v=kZIrIQDf1nQ"});
        learningResources.add(new String[]{"How to Calculate Your Carbon Footprint", "https://www.carbonfootprint.com/calculator.aspx"});
        learningResources.add(new String[]{"Understanding Renewable Energy Sources", "https://www.nrdc.org/stories/renewable-energy-clean-facts"});
        learningResources.add(new String[]{"The Circular Economy: A Beginner’s Guide", "https://www.youtube.com/watch?v=zkoSmXxFeoY"});
        learningResources.add(new String[]{"Reducing Plastic Use: A Practical Guide", "https://www.youtube.com/watch?v=K9ojpJBSTw8"});
        learningResources.add(new String[]{"Sustainable Fashion: Shopping Tips for Eco-Conscious Consumers", "https://www.buff.com/blog/en/green-lifestyle/tips-sustainable-shopping/"});
        learningResources.add(new String[]{"The Role of Wind Energy in Fighting Climate Change", "https://www.youtube.com/watch?v=MS3kA7gIWfg"});
        learningResources.add(new String[]{"The Rise of Electric Vehicles: What You Need to Know", "https://www.panion.org/everything_about_evs/"});
        learningResources.add(new String[]{"Understanding Carbon Footprints", "https://www.youtube.com/watch?v=8q7_aV8eLUE"});
        learningResources.add(new String[]{"Top 10 Sustainable Living Tips", "https://www.outdoors.org/resources/amc-outdoors/conservation-and-climate/go-green-10-tips-for-a-more-sustainable-lifestyle/"});
        learningResources.add(new String[]{"Smart Home Technologies", "https://www.youtube.com/watch?v=WHXYlEB_QmY"});
        learningResources.add(new String[]{"Climate Change Explained", "https://climate.nasa.gov/resources/education/"});
        learningResources.add(new String[]{"The Science Behind Wind Turbines", "https://www.energy.gov/eere/wind/how-wind-turbines-work"});
        learningResources.add(new String[]{"Green Buildings: A Comprehensive Guide", "https://www.worldgbc.org/what-green-building"});
        learningResources.add(new String[]{"The Power of Geothermal Energy", "https://www.youtube.com/watch?v=JWCE5ld4_yM"});
        learningResources.add(new String[]{"Composting 101", "https://www.epa.gov/recycle/composting-home"});
        learningResources.add(new String[]{"Recycling: How It Works", "https://www.youtube.com/watch?v=hGp1o1rS-cY"});
        learningResources.add(new String[]{"Water Conservation Tips", "https://wateruseitwisely.com/100-ways-to-conserve/"});
        learningResources.add(new String[]{"How to Start a Community Garden", "https://www.youtube.com/watch?v=MjzUe-P3shs"});
        learningResources.add(new String[]{"The Impact of Deforestation", "https://www.nationalgeographic.com/environment/article/deforestation"});
        learningResources.add(new String[]{"Ocean Conservation: Protecting Marine Life", "https://oceanconservancy.org/protecting-ocean-life/"});
        learningResources.add(new String[]{"Renewable Energy Trends 2024", "https://www.iea.org/reports/renewables-2024"});
        learningResources.add(new String[]{"Sustainable Tourism: Tips for Eco-Friendly Travel", "https://sustainabletourism.net/"});
        learningResources.add(new String[]{"Understanding Biomass Energy", "https://www.energy.gov/eere/bioenergy/biomass-resources"});
        learningResources.add(new String[]{"Energy Efficiency in Everyday Life", "https://www.energy.gov/energysaver/energy-saver"});
        learningResources.add(new String[]{"The Role of AI in Sustainability", "https://www.youtube.com/watch?v=4tpP8iGEKbs"});
        learningResources.add(new String[]{"Hydropower: The World’s Oldest Renewable Energy", "https://www.energy.gov/eere/water/how-hydropower-works"});
        learningResources.add(new String[]{"Eco-Friendly Habits for the Workplace", "https://www.greenofficemovement.org/eco-friendly-workplace/"});
        learningResources.add(new String[]{"The Future of Sustainable Agriculture", "https://www.youtube.com/watch?v=OSdCnGF7W7E"});
        learningResources.add(new String[]{"What is Net Zero?", "https://www.youtube.com/watch?v=63TX5eK8fBw"});
        learningResources.add(new String[]{"Green Transportation Options", "https://www.ucsusa.org/resources/clean-transportation-solutions"});
        learningResources.add(new String[]{"The Benefits of Urban Green Spaces", "https://www.youtube.com/watch?v=_1UeNKuBIVY"});

        // Add market trends
        marketTrends.add("Renewable energy investments increased by 30% in 2024.");
        marketTrends.add("Sustainable fashion brands see 50% growth in sales.");
        marketTrends.add("Global carbon offset purchases reach a record high.");
        marketTrends.add("Electric vehicle sales surpass 10 million units worldwide.");
        marketTrends.add("Wind energy becomes the fastest-growing power source globally.");
        marketTrends.add("Major corporations pledge to achieve net-zero emissions by 2030.");
        marketTrends.add("Demand for solar panel installations rises by 40% in urban areas.");
        marketTrends.add("Hydropower adoption accelerates in developing countries.");
        marketTrends.add("Plastic recycling initiatives expand to more than 50 countries.");
        marketTrends.add("Consumer interest in green home solutions doubles in 2024.");
        marketTrends.add("Government subsidies for renewable energy projects reach new highs.");
        marketTrends.add("Smart agriculture practices reduce water usage by 20%.");
        marketTrends.add("Biodegradable packaging gains popularity among global retailers.");
        marketTrends.add("Electric buses replace diesel fleets in major cities worldwide.");
        marketTrends.add("Carbon capture technologies see significant breakthroughs.");
        marketTrends.add("Energy-efficient appliances dominate household purchases.");
        marketTrends.add("Global wind turbine installations grow by 25% annually.");
        marketTrends.add("Sustainable tourism practices adopted by 70% of travel agencies.");
        marketTrends.add("Corporate sustainability reports show a marked improvement.");
        marketTrends.add("Circular economy initiatives gain traction among global corporations.");
        marketTrends.add("Ocean conservation programs receive record donations.");
        marketTrends.add("Battery storage capacity for renewable energy increases tenfold.");
        marketTrends.add("Waste-to-energy technologies implemented in more urban areas.");
        marketTrends.add("Eco-friendly building certifications become industry standard.");
        marketTrends.add("Reusable water bottles replace single-use plastics in workplaces.");
        marketTrends.add("Demand for compostable products increases in major markets.");
        marketTrends.add("Green energy jobs grow faster than any other sector.");
        marketTrends.add("Blockchain technology used for tracking sustainability metrics.");
        marketTrends.add("Microgrid solutions gain popularity in rural electrification.");
        marketTrends.add("Global carbon trading market reaches $300 billion valuation.");
        marketTrends.add("Major tech companies invest in green data centers.");
        marketTrends.add("Regenerative agriculture practices expand to 10 million acres.");
        marketTrends.add("Plant-based food products see exponential growth in sales.");
        marketTrends.add("Algae biofuel research receives significant government funding.");
        marketTrends.add("Eco-label certifications influence consumer purchasing decisions.");
        marketTrends.add("Green bonds become a key tool for funding sustainability projects.");
        marketTrends.add("Wildlife conservation efforts see a 30% increase in global funding.");
        marketTrends.add("Demand for rooftop solar panels skyrockets in suburban areas.");
        marketTrends.add("Carbon footprint tracking apps gain popularity among millennials.");
        marketTrends.add("Eco-friendly startups attract record levels of venture capital.");
        marketTrends.add("Recycling rates increase in developed nations due to new policies.");
        marketTrends.add("Corporate greenwashing under scrutiny with stricter regulations.");
        marketTrends.add("Demand for refurbished electronics rises among eco-conscious consumers.");
        marketTrends.add("Tree-planting campaigns exceed targets in over 20 countries.");
        marketTrends.add("Sustainable shipping solutions adopted by leading logistics companies.");
        marketTrends.add("Investment in electric aviation startups reaches new heights.");
        marketTrends.add("Rainwater harvesting systems become a standard feature in new homes.");
        marketTrends.add("Global awareness campaigns boost interest in renewable energy.");
        marketTrends.add("Bioplastics market projected to grow by 15% annually.");
        marketTrends.add("Advanced recycling technologies reduce waste-to-landfill by 50%.");
        marketTrends.add("Climate tech companies dominate IPO markets in 2024.");

        // Add eco-friendly products
        ecoProducts.add("Smart Thermostat: Reduce energy bills by up to 20%.");
        ecoProducts.add("Bamboo Toothbrush: Biodegradable and eco-friendly.");
        ecoProducts.add("Solar Panel Kit: Affordable renewable energy for your home.");
        ecoProducts.add("Reusable Water Bottle: Stay hydrated and reduce plastic waste.");
        ecoProducts.add("LED Light Bulbs: Long-lasting and energy-efficient lighting.");
        ecoProducts.add("Compost Bin: Turn food waste into nutrient-rich soil.");
        ecoProducts.add("Electric Bike: Eco-friendly commuting option.");
        ecoProducts.add("Organic Cotton T-shirts: Sustainably sourced and stylish.");
        ecoProducts.add("Solar-Powered Charger: Charge your devices using the sun.");
        ecoProducts.add("Reusable Shopping Bags: Durable and convenient.");
        ecoProducts.add("Plant-Based Laundry Detergent: Gentle on clothes and the environment.");
        ecoProducts.add("Biodegradable Phone Cases: Protect your phone sustainably.");
        ecoProducts.add("Eco-Friendly Yoga Mat: Made from recycled materials.");
        ecoProducts.add("Smart Plugs: Monitor and reduce electricity usage.");
        ecoProducts.add("Recycled Paper Notebooks: Perfect for eco-conscious students.");
        ecoProducts.add("Indoor Air Purifier: Breathe cleaner air at home.");
        ecoProducts.add("Energy-Efficient Refrigerator: Save energy and keep food fresh.");
        ecoProducts.add("Solar Lantern: Ideal for camping and emergencies.");
        ecoProducts.add("Reusable Coffee Cups: Reduce waste from single-use cups.");
        ecoProducts.add("Eco-Friendly Dishwashing Soap: Cleans effectively without harming nature.");
        ecoProducts.add("Compostable Trash Bags: Break down naturally after use.");
        ecoProducts.add("Rainwater Harvesting System: Save water for gardening needs.");
        ecoProducts.add("Low-Flow Showerheads: Conserve water during daily showers.");
        ecoProducts.add("Natural Beeswax Wraps: Replace plastic cling film.");
        ecoProducts.add("Reusable Straw Set: Made from stainless steel or bamboo.");
        ecoProducts.add("Solar Backpack: Charge devices on the go.");
        ecoProducts.add("Energy-Efficient Washing Machine: Conserve water and electricity.");
        ecoProducts.add("Reclaimed Wood Furniture: Sustainable and stylish home decor.");
        ecoProducts.add("Recycled Plastic Sunglasses: Fashionable and planet-friendly.");
        ecoProducts.add("Organic Skincare Products: Free from harmful chemicals.");
        ecoProducts.add("Eco-Friendly Cleaning Supplies: Safe for your home and the environment.");
        ecoProducts.add("Electric Lawn Mower: Quiet and emissions-free.");
        ecoProducts.add("Solar Water Heater: Reduce energy consumption for hot water.");
        ecoProducts.add("Reusable Food Storage Bags: A sustainable alternative to zip bags.");
        ecoProducts.add("Carbon Offset Subscription: Support renewable energy projects.");
        ecoProducts.add("Sustainable Sneakers: Made from recycled materials.");
        ecoProducts.add("Solar String Lights: Perfect for outdoor decoration.");
        ecoProducts.add("Energy Star Appliances: Certified for energy efficiency.");
        ecoProducts.add("Reusable Makeup Remover Pads: Eco-friendly beauty accessory.");
        ecoProducts.add("Portable Water Purifier: Clean drinking water anywhere.");
        ecoProducts.add("Compostable Plates and Utensils: Perfect for picnics and parties.");
        ecoProducts.add("Hybrid Cars: Low emissions and high efficiency.");
        ecoProducts.add("Smart Water Meters: Track and reduce water usage.");
        ecoProducts.add("Recycled Plastic Rugs: Durable and eco-friendly home addition.");
        ecoProducts.add("Plant-Based Protein Powder: Sustainable nutrition option.");
        ecoProducts.add("Eco-Friendly Paint: Non-toxic and low in volatile organic compounds.");
        ecoProducts.add("Organic Fertilizer: Boost plant growth without harmful chemicals.");
        ecoProducts.add("Electric Snow Blower: Efficient and emissions-free snow removal.");
        ecoProducts.add("Sustainable Wood Flooring: Eco-friendly home improvement.");
        ecoProducts.add("Natural Pest Repellents: Safe for homes with pets and children.");
    }

    private void displayRandomContent() {
        // Randomly pick a learning resource
        String[] learningResource = learningResources.get(random.nextInt(learningResources.size()));
        learningResourceTextView.setText("Latest Resource: " + learningResource[0]);

        // Randomly pick a market trend
        String marketTrend = marketTrends.get(random.nextInt(marketTrends.size()));
        marketTrendTextView.setText("Latest Trend: " + marketTrend);

        // Randomly pick an eco-friendly product
        String ecoProduct = ecoProducts.get(random.nextInt(ecoProducts.size()));
        ecoProductTextView.setText("Product Highlight: " + ecoProduct);
    }

    private void openUrl(String url) {
        // Open the provided URL in a browser
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }

    private void enableBackToHomepage() {
        // Add a back button at the top of the activity (optional)
        findViewById(R.id.backButton).setOnClickListener(v -> {
            //Intent intent = new Intent(EcoHubActivity.this, HomePageActivity.class);
            Intent intent = new Intent(EcoHubActivity.this, MainDashboard.class);
            startActivity(intent);
            finish();
        });
    }
}