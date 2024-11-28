package com.example.planetz.model;


import java.util.HashMap;
import java.util.Map;

public class CountryAverage {

    private static CountryAverage instance;

    private final Map<String, Double> averages;

    private CountryAverage() {
        averages = new HashMap<>();
        averages.put("Afghanistan", 0.29536375);
        averages.put("Africa", 0.99422127);
        averages.put("Albania", 1.7432004);
        averages.put("Algeria", 3.9272263);
        averages.put("Andorra", 4.6171236);
        averages.put("Angola", 0.45155162);
        averages.put("Anguilla", 8.752724);
        averages.put("Antigua and Barbuda", 6.4218745);
        averages.put("Argentina", 4.2378173);
        averages.put("Armenia", 2.3045583);
        averages.put("Aruba", 8.133404);
        averages.put("Asia", 4.611434);
        averages.put("Asia (excl. China and India)", 4.017375);
        averages.put("Australia", 14.985412);
        averages.put("Austria", 6.8781943);
        averages.put("Azerbaijan", 3.6746833);
        averages.put("Bahamas", 5.1708703);
        averages.put("Bahrain", 25.672274);
        averages.put("Bangladesh", 0.5964455);
        averages.put("Barbados", 4.3772573);
        averages.put("Belarus", 6.1669006);
        averages.put("Belgium", 7.6875386);
        averages.put("Belize", 1.7894346);
        averages.put("Benin", 0.631487);
        averages.put("Bermuda", 6.9370627);
        averages.put("Bhutan", 1.3489918);
        averages.put("Bolivia", 1.7583066);
        averages.put("Bonaire Sint Eustatius and Saba", 4.083284);
        averages.put("Bosnia and Herzegovina", 6.1034565);
        averages.put("Botswana", 2.838951);
        averages.put("Brazil", 2.2454574);
        averages.put("British Virgin Islands", 5.0039577);
        averages.put("Brunei", 23.950201);
        averages.put("Bulgaria", 6.8044534);
        averages.put("Burkina Faso", 0.26295447);
        averages.put("Burundi", 0.06194545);
        averages.put("Cambodia", 1.1900775);
        averages.put("Cameroon", 0.34292704);
        averages.put("Canada", 14.249212);
        averages.put("Cape Verde", 0.9588915);
        averages.put("Central African Republic", 0.040548485);
        averages.put("Chad", 0.13367727);
        averages.put("Chile", 4.3041654);
        averages.put("China", 7.992761);
        averages.put("Colombia", 1.9223082);
        averages.put("Comoros", 0.49327007);
        averages.put("Congo", 1.2447897);
        averages.put("Cook Islands", 3.9950094);
        averages.put("Costa Rica", 1.5226681);
        averages.put("Cote d'Ivoire", 0.41668788);
        averages.put("Croatia", 4.348515);
        averages.put("Cuba", 1.8659163);
        averages.put("Curacao", 9.189007);
        averages.put("Cyprus", 5.616782);
        averages.put("Czechia", 9.3357525);
        averages.put("Democratic Republic of Congo", 0.036375992);
        averages.put("Denmark", 4.940161);
        averages.put("Djibouti", 0.40418932);
        averages.put("Dominica", 2.1058853);
        averages.put("Dominican Republic", 2.1051137);
        averages.put("East Timor", 0.49869007);
        averages.put("Ecuador", 2.3117273);
        averages.put("Egypt", 2.333106);
        averages.put("El Salvador", 1.2174718);
        averages.put("Equatorial Guinea", 3.0307202);
        averages.put("Eritrea", 0.18914719);
        averages.put("Estonia", 7.77628);
        averages.put("Eswatini", 1.0527312);
        averages.put("Ethiopia", 0.15458965);
        averages.put("Europe", 6.8578663);
        averages.put("Europe (excl. EU-27)", 7.886797);
        averages.put("Europe (excl. EU-28)", 8.817789);
        averages.put("European Union (27)", 6.1743994);
        averages.put("European Union (28)", 5.983708);
        averages.put("Faroe Islands", 14.084624);
        averages.put("Fiji", 1.1550449);
        averages.put("Finland", 6.5267396);
        averages.put("France", 4.603891);
        averages.put("French Polynesia", 2.8509297);
        averages.put("Gabon", 2.3882635);
        averages.put("Gambia", 0.2847278);
        averages.put("Georgia", 2.962545);
        averages.put("Germany", 7.9837584);
        averages.put("Ghana", 0.6215505);
        averages.put("Greece", 5.7451057);
        averages.put("Greenland", 10.473997);
        averages.put("Grenada", 2.7133646);
        averages.put("Guatemala", 1.0756185);
        averages.put("Guinea", 0.35742033);
        averages.put("Guinea-Bissau", 0.15518051);
        averages.put("Guyana", 4.3736935);
        averages.put("Haiti", 0.21119381);
        averages.put("High-income countries", 10.132565);
        averages.put("Honduras", 1.0696708);
        averages.put("Hong Kong", 4.081913);
        averages.put("Hungary", 4.449911);
        averages.put("Iceland", 9.499798);
        averages.put("India", 1.9966822);
        averages.put("Indonesia", 2.6456614);
        averages.put("Iran", 7.7993317);
        averages.put("Iraq", 4.024638);
        averages.put("Ireland", 7.7211185);
        averages.put("Israel", 6.208912);
        averages.put("Italy", 5.726825);
        averages.put("Jamaica", 2.2945588);
        averages.put("Japan", 8.501681);
        averages.put("Jordan", 2.0301995);
        averages.put("Kazakhstan", 13.979704);
        averages.put("Kenya", 0.45998666);
        averages.put("Kiribati", 0.5184742);
        averages.put("Kosovo", 4.830646);
        averages.put("Kuwait", 25.578102);
        averages.put("Kyrgyzstan", 1.4251612);
        averages.put("Laos", 3.0803475);
        averages.put("Latvia", 3.561689);
        averages.put("Lebanon", 4.3543963);
        averages.put("Lesotho", 1.3594668);
        averages.put("Liberia", 0.1653753);
        averages.put("Libya", 9.242238);
        averages.put("Liechtenstein", 3.8097827);
        averages.put("Lithuania", 4.606163);
        averages.put("Low-income countries", 0.28005043);
        averages.put("Lower-middle-income countries", 1.777996);
        averages.put("Luxembourg", 11.618432);
        averages.put("Macao", 1.5127679);
        averages.put("Madagascar", 0.14871116);
        averages.put("Malawi", 0.10262384);
        averages.put("Malaysia", 8.576508);
        averages.put("Maldives", 3.2475724);
        averages.put("Mali", 0.31153768);
        averages.put("Malta", 3.1035979);
        averages.put("Marshall Islands", 3.6353714);
        averages.put("Mauritania", 0.957337);
        averages.put("Mauritius", 3.2697906);
        averages.put("Mexico", 4.0153365);
        averages.put("Micronesia (country)", 1.3243006);
        averages.put("Moldova", 1.6565942);
        averages.put("Mongolia", 11.150772);
        averages.put("Montenegro", 3.6558185);
        averages.put("Montserrat", 4.8447766);
        averages.put("Morocco", 1.8263615);
        averages.put("Mozambique", 0.24274588);
        averages.put("Myanmar", 0.6445672);
        averages.put("Namibia", 1.5399038);
        averages.put("Nauru", 4.1700416);
        averages.put("Nepal", 0.5074035);
        averages.put("Netherlands", 7.1372175);
        averages.put("New Caledonia", 17.641167);
        averages.put("New Zealand", 6.212154);
        averages.put("Nicaragua", 0.79879653);
        averages.put("Niger", 0.116688);
        averages.put("Nigeria", 0.5891771);
        averages.put("Niue", 3.8729508);
        averages.put("North America", 10.5346775);
        averages.put("North America (excl. USA)", 4.741475);
        averages.put("North Korea", 1.9513915);
        averages.put("North Macedonia", 3.6245701);
        averages.put("Norway", 7.5093055);
        averages.put("Oceania", 9.85179);
        averages.put("Oman", 15.730261);
        averages.put("Pakistan", 0.84893465);
        averages.put("Palau", 12.123921);
        averages.put("Palestine", 0.6660658);
        averages.put("Panama", 2.699258);
        averages.put("Papua New Guinea", 0.77131313);
        averages.put("Paraguay", 1.3299496);
        averages.put("Peru", 1.7891879);
        averages.put("Philippines", 1.3014648);
        averages.put("Poland", 8.106886);
        averages.put("Portugal", 4.050785);
        averages.put("Qatar", 37.601273);
        averages.put("Romania", 3.739777);
        averages.put("Russia", 11.416899);
        averages.put("Rwanda", 0.112346195);
        averages.put("Saint Helena", 3.2986484);
        averages.put("Saint Kitts and Nevis", 4.708081);
        averages.put("Saint Lucia", 2.6149206);
        averages.put("Saint Pierre and Miquelon", 10.293288);
        averages.put("Saint Vincent and the Grenadines", 2.2964725);
        averages.put("Samoa", 1.1218625);
        averages.put("Sao Tome and Principe", 0.5816142);
        averages.put("Saudi Arabia", 18.197495);
        averages.put("Senegal", 0.6738352);
        averages.put("Serbia", 6.024712);
        averages.put("Seychelles", 6.1495123);
        averages.put("Sierra Leone", 0.13124847);
        averages.put("Singapore", 8.911513);
        averages.put("Sint Maarten (Dutch part)", 14.352394);
        averages.put("Slovakia", 6.051555);
        averages.put("Slovenia", 5.9979916);
        averages.put("Solomon Islands", 0.41232163);
        averages.put("Somalia", 0.03676208);
        averages.put("South Africa", 6.7461643);
        averages.put("South America", 2.4865332);
        averages.put("South Korea", 11.598764);
        averages.put("South Sudan", 0.1680176);
        averages.put("Spain", 5.1644425);
        averages.put("Sri Lanka", 0.7936504);
        averages.put("Sudan", 0.4696261);
        averages.put("Suriname", 5.8029985);
        averages.put("Sweden", 3.6069093);
        averages.put("Switzerland", 4.0478554);
        averages.put("Syria", 1.2490375);
        averages.put("Taiwan", 11.630868);
        averages.put("Tajikistan", 1.0064901);
        averages.put("Tanzania", 0.23771806);
        averages.put("Thailand", 3.7762568);
        averages.put("Togo", 0.2910665);
        averages.put("Tonga", 1.7686282);
        averages.put("Trinidad and Tobago", 22.423758);
        averages.put("Tunisia", 2.879285);
        averages.put("Turkey", 5.1052055);
        averages.put("Turkmenistan", 11.03418);
        averages.put("Turks and Caicos Islands", 7.636793);
        averages.put("Tuvalu", 1.0004411);
        averages.put("Uganda", 0.12744623);
        averages.put("Ukraine", 3.5578535);
        averages.put("United Arab Emirates", 25.833244);
        averages.put("United Kingdom", 4.7201805);
        averages.put("United States", 14.949616);
        averages.put("Upper-middle-income countries", 6.2268133);
        averages.put("Uruguay", 2.3060381);
        averages.put("Uzbekistan", 3.4830604);
        averages.put("Vanuatu", 0.6363055);
        averages.put("Venezuela", 2.7168686);
        averages.put("Vietnam", 3.4995174);
        averages.put("Wallis and Futuna", 2.2819076);
        averages.put("World", 4.658219);
        averages.put("Yemen", 0.33701748);
        averages.put("Zambia", 0.44570068);
        averages.put("Zimbabwe", 0.542628);

    }

    // 获取单例实例
    public static synchronized CountryAverage getInstance() {
        if (instance == null) {
            instance = new CountryAverage();
        }
        return instance;
    }

    // 获取某项平均值
    public Double getAverage(String key) {
        return averages.getOrDefault(key, 0.0); // 如果没有找到对应键，返回默认值0.0
    }

    // 更新某项平均值
    public void setAverage(String key, Double value) {
        averages.put(key, value);
    }

    // 获取所有平均值
    public Map<String, Double> getAllAverages() {
        return new HashMap<>(averages); // 返回一个副本，避免直接修改
    }

    // 更新所有平均值
    public void setAllAverages(Map<String, Double> newAverages) {
        averages.clear();
        averages.putAll(newAverages);
    }

    // 打印所有平均值（调试用）
    @Override
    public String toString() {
        return "GlobalAverages{" +
                "averages=" + averages +
                '}';
    }
}

