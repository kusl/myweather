00
55

from what I can tell the app is missing the capability to show watch and warning on top prominently 
also when I click on one of the "upcoming" boxes I would like to see as much detailed information as possible 
please make sure that we actually cache every single request as much as possible 
for example, on a sister project I learned that nws sends 404 responses that we should cache as well 
We need to cache 404 results as well

{
    "correlationId": "1d604a85",
    "title": "Marine Forecast Not Supported",
    "type": "https://api.weather.gov/problems/MarineForecastNotSupported",
    "status": 404,
    "detail": "Forecasts for marine areas are not yet supported by this API.",
    "instance": "https://api.weather.gov/requests/1d604a85"
}
Not sure how this would work...
but basically the idea is if we reach out to nws and it sends a response, we shouldn't make that request again for a while 
and if the user tries, we should intercept any such request with the app 
so like if the gps coordinate says 0.00000 and we request a tile 
and then the gps coordinate says 0.00001 and we request the same tile 
we should intercept such requests because the endpoint has not changed 
we really want to be model citizens and write the perfect caching code 
however, we also want to get as much information as possible and surface as much information as possible to the users 
for watch and warning I think we have to present the information as is in a collapsible very prominently as this is life and death 
but even other than that if we have a json response 
and it has more endpoints we can pull from for more information we should get it all for that location
and present it all to the user 
