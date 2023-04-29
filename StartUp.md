The server is done in mongodb. In order to post to it i used nodejs which is found in WebServerSource. In order to add data to it you will need to replace(not add because if you dont erase the last entries they will be posted again) the last entries with what you want to enter.
Example from added documents:
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