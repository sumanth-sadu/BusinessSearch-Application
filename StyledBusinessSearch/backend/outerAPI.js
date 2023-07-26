const express = require('express');
const url = require('url');
const https = require('https');
const axios = require('axios');
const router = express.Router();


const yelpAPIkey = 'eFjfsnyNaDucwiclVODe6g8o8EPqpPqYV8w6FEoVcRqr47zd_knY4hJOqcnpOvH5M9WqAEoEuMKtM_VKUUTWxK6RTvE3jgrgasFzmfhHD61ZV2LniP8lxbvDBcQoY3Yx';

// module.exports.getBusinessDetails = getBusinessDetails;
// module.exports.getAutocomplete = getAutocomplete;

router.get('/', function(req, res) {
    keyword = req.query.keyword;
    category = req.query.category;
    distance = parseInt(req.query.distance);
    latitude = req.query.latitude;
    longitude = req.query.longitude;

    let baseURL = 'https://api.yelp.com/v3/businesses/search';
    const config = {
        headers:{
            'Authorization': `Bearer ${yelpAPIkey}`
        },
        params: {
            'term' : keyword,
            'latitude' : latitude,
            'longitude' : longitude,
            'categories' : category,
            'radius' : distance

        }
    } 
    // res.send('abc')
        
    axios.get(baseURL, config).then(response => {
        console.log(response.content)
        res.json(response.data)
    }).catch(err => {
        res.send(err);
    })
})


router.get('/:id', function(req, res) {
    let id = req.params.id;
    let baseURL = "https://api.yelp.com/v3/businesses/" + id;
    const config = {
        headers:{
            'Authorization': `Bearer ${yelpAPIkey}`
        }
    } 
    axios.get(baseURL, config).then(response => {
        console.log(response.data)
        res.json(response.data)
    }).catch(err => {
        res.send(err);
    })

})

module.exports = router;


// async function getBusinessDetails(keyword, category, distance, latitude, longitude){
//     const baseURL = 'https://api.yelp.com/v3/businesses/search'
//     const config = {
//         headers:{
//             'Authorization': `Bearer ${yelpAPIkey}`
//         },
//         params: {
//             'term' : keyword,
//             'latitude' : latitude,
//             'longitude' : longitude,
//             'categories' : category,
//             'radius' : distance
//         }
//     } 

//     console.log(config)

//     // router.get('/', function(req, res) {
//     //     axios(baseURL, config).then(response => {
//     //         console.log('now')
//     //         console.log(response.data)
//     //         console.log(res.json(response.data))
//     //         res.json(response.data)
//     //     }).catch(err => {
//     //         res.send(err);
//     //     })
//         // })

//     const get_response = await axios(baseURL, config);

//     console.log(get_response)
//     console.log(get_response.data)

// }

// async function getAutocomplete(keyword, latitude, longitude) {

//     const baseURL = 'https://api.yelp.com/v3/autocomplete'
//     const config = {
//         headers:{
//             'Authorization': `Bearer ${yelpAPIkey}`
//         },
//         params: {
//             'text' : keyword,
//             'latitude' : latitude,
//             'longitude' : longitude,
//         }
//     } 

//     const get_response = await axios(baseURL, config)

//     return get_response.data
// }


// async function getCompanyMetaData(tickerName) {
//     // table 4.1 content: Company’s Meta Data API call
//     let url = `https://api.tiingo.com/tiingo/daily/${tickerName}?token=${tiingoAPIkey}`;
//     let headers = {'Content-Type': 'application/json'};
//     let APIres = await fetch(url, {method: 'GET', headers: headers});
//     let metaDataRes = await APIres.json();
//     return metaDataRes;
// }

// async function getLatestPrice(tickerName) {
//     // table 4.2 content: Company’s Latest Price of the Stock
//     let url = `https://api.tiingo.com/iex/?tickers=${tickerName}&token=${tiingoAPIkey}`;
//     let headers = {'Content-Type': 'application/json'};
//     let APIres = await fetch(url, {method: 'GET', headers: headers});
//     let latestPriceRes = await APIres.json();
//     if (latestPriceRes.length === 0) {
//         return {"detail": "Not found."};
//     } else {
//         return latestPriceRes[0];
//     }

// }


// async function getNews(keyword) {
//     let url = `https://newsapi.org/v2/everything?q=${keyword}&apiKey=${newsAPIkey}&pageSize=20&page=1`;
//     let headers = {'Content-Type': 'application/json'};
//     let APIres = await fetch(url, {
//         method: 'GET',
//         headers: headers,
//         // agent: new HttpsProxyAgent('http://127.0.0.1:1087')
//     }).catch(error => {
//         console.log("News fetch failed.");
//     });
//     let newsRes;
//     if (APIres == null) {
//         newsRes = null;
//     } else {
//         let origRes = await APIres.json();
//         let totalResults = await origRes.totalResults;
//         if (totalResults == 0) {
//             newsRes = [];
//         } else {
//             newsRes = await origRes.articles;
//         }
//     }
//     return newsRes;
// }

// async function getDailyChartData(startDate, tickerName) {
//     // Company’s Last day’s chart data (close price)
//     let url = `https://api.tiingo.com/iex/${tickerName}/prices?startDate=${startDate}&resampleFreq=4min&columns=close&token=${tiingoAPIkey}`;
//     let headers = {'Content-Type': 'application/json'};
//     let APIres = await fetch(url, {method: 'GET', headers: headers});
//     let dailyPriceRes = await APIres.json();
//     return dailyPriceRes;
// }

// async function getHistChartsData(startDate, tickerName) {
//     // Company’s Historical data in the last 2 years
//     let url = `https://api.tiingo.com/tiingo/daily/${tickerName}/prices?startDate=${startDate}&resampleFreq=daily&token=${tiingoAPIkey}`;
//     let headers = {'Content-Type': 'application/json'};
//     let APIres = await fetch(url, {method: 'GET', headers: headers});
//     let histRes = await APIres.json();
//     return histRes;
// }