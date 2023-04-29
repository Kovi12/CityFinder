const MongoClient = require('mongodb').MongoClient;

async function main() {
  const uri = "mongodb+srv://Kovi12:ghibea123@cluster.fedof16.mongodb.net/?retryWrites=true&w=majority";
  const client = new MongoClient(uri, { useNewUrlParser: true, useUnifiedTopology: true });
  try {
    await client.connect();
    await listDB(client);
    await createMultipleListings(client, [
      {
        name: "Helsinki",
        population: [650000, 1500000], //normal, metropolitan
        area_occupied: [715.48,3697.52],     //normal, metropolitan square meters
        population_density: 1418,      // per square km
        timezone: "UTC+02:00",
        airport: "Helsinki Airport",
        currency: "Euro",
        attractions: ["National Museum of Finland", "Helsinky City Museum", "University of Helsinki"],
        best_known: "Innovative art, culture and architecture",
        temperature: [16.5, -5.5],      // highest, lowest in Celsius
        landform: "Peninsula",
        coords: [60.1699, 24.9384]      //lat, long  
      },

      {
        name: "Oslo",
        population: [1064235, 1546706], //normal, metropolitan
        area_occupied: [480,null],     //normal, metropolitan square meters
        population_density: 1442,      // per square km
        timezone: "UTC+1",
        airport: "Oslo Airport",
        currency: "Norwegian Kroner",
        attractions: ["Akershus Fortress", "The Vigeland Park", "The Norwegian Museum of Cultural History"],
        best_known: "Viking and nautical history",
        temperature: [17.5, -3],      // highest, lowest in Celsius
        landform: "Green hills",
        coords: [59.9139, 10.7522]      //lat, long  
      },

      {
        name: "Copenhagen",
        population: [518574, 1875179], //normal, metropolitan
        area_occupied: [179.8, null],     //normal, metropolitan square meters
        population_density: 5876,      // per square km
        timezone: "CET (+1)",
        airport: "Copenhagen Airport",
        currency: "Danish Krone",
        attractions: ["Tivoli Gardens", "Rosenborg Castle", "Amalienborg"],
        best_known: "The city of fairy tales",
        temperature: [17, 0],      // highest, lowest in Celsius
        landform: "Flat Hills",
        coords: [55.6761, 12.5683]      //lat, long  
      },

      {
        name: "Bucharest",
        population: [1716983, 2259669], //normal, metropolitan
        area_occupied: [240,1804],     //normal, metropolitan square meters
        population_density: 7154,      // per square km
        timezone: "UTC+02:00",
        airport: "Bucharest Otopeni Airport",
        currency: "Romanian Leu",
        attractions: ["Romanian Athenaeum", "Palace of Parliament", "Gradina Cismigiu"],
        best_known: "Its wide, tree-lined boulevards, glorious Belle Époque buildings and a reputation for the high life",
        temperature: [33, -5],      // highest, lowest in Celsius
        landform: "Plain",
        coords: [44.4268, 26.1025]      //lat, long  
      },

      {
        name: "Berlin",
        population: [3677472, 6144600], //normal, metropolitan
        area_occupied: [891.3,30546],     //normal, metropolitan square meters
        population_density: 4126,      // per square km
        timezone: "UTC+01:00",
        airport: "Berlin-Brandenburg Airport",
        currency: "Euro",
        attractions: ["Brandenburg Gate", "Berliner Fernsehturm", "Reichstag Building"],
        best_known: "Exceptional range of landmarks, vibrant cultural scene and way of life that's somehow all go yet relaxed",
        temperature: [25, 3],      // highest, lowest in Celsius
        landform: "Glacial Valley",
        coords: [52.5200, 13.4050]      //lat, long  
      },

      {
        name: "Kyiv",
        population: [2962180, 3475000], //normal, metropolitan
        area_occupied: [839,null],     //normal, metropolitan square meters
        population_density: 3299,      // per square km
        timezone: "UTC+02:00",
        airport: "Boryspil International Airport",
        currency: "Hryvnia",
        attractions: ["Ukrainian Motherland Monument", "Kiev Pechersk Lavra", "Golden Gate"],
        best_known: "Cultural Sites",
        temperature: [21.6, -3.1],      // highest, lowest in Celsius
        landform: "Hills",
        coords: [50.4501, 30.5234]      //lat, long  
      },

      {
        name: "Budapest",
        population: [1752286, 3011598], //normal, metropolitan
        area_occupied: [525.2,7626],     //normal, metropolitan square meters
        population_density: 3388,      // per square km
        timezone: "UTC+1",
        airport: "Budapest Ferenc Liszt International Airport",
        currency: "Hungarian Forint",
        attractions: ["St. Stephen's Basilica", "Hungarian Parliament Building", "Fisherman's Bastion"],
        best_known: "World's Spa capital",
        temperature: [21, -1],      // highest, lowest in Celsius
        landform: "Hills",
        coords: [47.4979, 19.0402]      //lat, long  
      },

      {
        name: "Warsaw",
        population: [1863056, 3100844], //normal, metropolitan
        area_occupied: [517.24,6100.43],     //normal, metropolitan square meters
        population_density: 3601,      // per square km
        timezone: "UTC+1",
        airport: "Chopin airport",
        currency: "Polish Zloty",
        attractions: ["The Royal Castle", "Palace of Culture and Science", "Uprising Museum"],
        best_known: "Imposing post-war architecture",
        temperature: [18, -2.5],      // highest, lowest in Celsius
        landform: "Plain",
        coords: [52.2297, 21.0122]      //lat, long  
      },

      {
        name: "London",
        population: [8799800, 14257962], //normal, metropolitan
        area_occupied: [1572.03,8382],     //normal, metropolitan square meters
        population_density: 5598,      // per square km
        timezone: "UTC",
        airport: "London City Airport",
        currency: "British Pound",
        attractions: ["Tower of London", "Buckingham Palace", "Tower Bridge"],
        best_known: "The largest metropolis in the United Kingdom",
        temperature: [19, 5],      // highest, lowest in Celsius
        landform: "Basin",
        coords: [51.5072, 0.1276]      //lat, long  
      },

      {
        name: "Amsterdam",
        population: [941402, 2480394], //normal, metropolitan
        area_occupied: [219.32,3043],     //normal, metropolitan square meters
        population_density: 5277,      // per square km
        timezone: "UTC+1",
        airport: "Amsterdam Schiphol Airport",
        currency: "Euro",
        attractions: ["Rijksmuseum", "Van Gigh Museum", "Anne Frank House"],
        best_known: "Beautiful canals",
        temperature: [16, 2],      // highest, lowest in Celsius
        landform: "Swamp",
        coords: [52.3676, 4.9041]      //lat, long  
      },

      {
        name: "Prague",
        population: [1275406, 2709418], //normal, metropolitan
        area_occupied: [496,11425],     //normal, metropolitan square meters
        population_density: 2600,      // per square km
        timezone: "UTC+1",
        airport: "Vaclav Havel Airport Prague",
        currency: "Czech Crown",
        attractions: ["Prague Castle", "Charles Bridge", "Prague Astronomical Clock"],
        best_known: "Well-preserved castles",
        temperature: [18, -1],      // highest, lowest in Celsius
        landform: "Hills",
        coords: [50.0755, 14.4378]      //lat, long  
      },

      {
        name: "Zagreb",
        population: [767131, 1107150], //normal, metropolitan
        area_occupied: [641,2911],     //normal, metropolitan square meters
        population_density: 380,      // per square km
        timezone: "UTC+1",
        airport: "Zagreb International Airport",
        currency: "Euro",
        attractions: ["Cathedral of Zagreb", "Museum of Broken Relationships", "St. Mark's Church"],
        best_known: "Impressive art galleries and museums",
        temperature: [21.2, 0.1],      // highest, lowest in Celsius
        landform: "Hills",
        coords: [45.8150, 15.9819]      //lat, long  
      }


    ])
  } catch (error) {
    console.error(error);
  } finally {
    await client.close();
  }

}




main().catch(console.error);

async function listDB(client) {
  const dblist = await client.db().admin().listDatabases();

  console.log("DB:");
  dblist.databases.forEach(db => {
    console.log('- ' + db.name);
  })
}

async function createMultipleListings(client, newListings) {
  const result = await client.db("CityFinderDB").collection("Cities").insertMany(newListings);

  console.log(result.insertedCount + 'New listing created with the following id(s): ');
  console.log(result.insertedIds);
}