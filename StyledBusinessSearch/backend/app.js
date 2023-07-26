// importing modules
const express = require('express');
const bodyparser = require('body-parser');
const cors = require('cors');
// const path = require('path');
const axios = require('axios');
const outerAPI = require('./outerAPI');

// port no.
const port = process.env.PORT || 8080;

const app = express();
app.use(cors());

app.use('/api/search', outerAPI)


app.use(express.static(process.cwd()+"/dist/frontend/"));

app.get('/', (req,res) => {
    res.sendFile(process.cwd()+"/dist/frontend/index.html")
  });

const yelpAPIkey = 'eFjfsnyNaDucwiclVODe6g8o8EPqpPqYV8w6FEoVcRqr47zd_knY4hJOqcnpOvH5M9WqAEoEuMKtM_VKUUTWxK6RTvE3jgrgasFzmfhHD61ZV2LniP8lxbvDBcQoY3Yx';

app.get('/api/loc', function(req,res) {
    let add = req.query.address;
    // console.log(add)

    add = add.replaceAll(' ', '+');

    var googlekey = 'AIzaSyBLEkKp9FHS7iECybzv9AesVs-fuoFXlIE';

    // "https://maps.googleapis.com/maps/api/geocode/json?address=" + location + "&key=" + googlekey, true);

    let get_res = axios('https://maps.googleapis.com/maps/api/geocode/json?address=' + add + '&key=' + googlekey)

    console.log(get_res)

    // var geometry = jsonResponse['results'][0]['geometry'];
    //             var lat = geometry['location']['lat']
    //             var lng = geometry['location']['lng']
})


app.get('/api/auto', function(req,res){

    let text = req.query.text;
    let autocomplete_url = 'https://api.yelp.com/v3/autocomplete?text=' + text;
    const config = {
        headers:{
            'Authorization': `Bearer ${yelpAPIkey}`
        }
    } 
    
    axios(autocomplete_url,config).then(response => {
        console.log(response.data)
        res.json(response.data)
    }).catch(err => {
        res.send(err);
    });
})

app.get('/api/moreinfo', function(req,res){

    let id = req.query.id;
    let moreinfo_url = 'https://api.yelp.com/v3/businesses/' + id;
    const config = {
        headers:{
            'Authorization': `Bearer ${yelpAPIkey}`
        }
    } 
    
    axios(moreinfo_url,config).then(response => {
        res.json(response.data)
    }).catch(err => {
        res.send(err);
    });

})

app.get('/api/reviews', function(req,res){

    let id = req.query.id;
    let reviews_url = 'https://api.yelp.com/v3/businesses/' + id + '/reviews';
    const config = {
        headers:{
            'Authorization': `Bearer ${yelpAPIkey}`
        }
    } 
    
    axios(reviews_url,config).then(response => {
        res.json(response.data)
    }).catch(err => {
        res.send(err);
    });
})


// app.get('/search', async function (req, res) {
    // keyword = req.query.keyword;
    // category = req.query.category;
    // distance = parseInt(req.query.distance);
    // latitude = req.query.latitude;
    // longitude = req.query.longitude;
    // keyword = 'pizza';
    // category = 'food';
    // distance = 16000;
    // latitude = '34.018160';
    // longitude = '-118.297270';

    // res = outerAPI
    // console.log(res)

    // res = await outerAPI.getBusinessDetails(keyword, category, distance, latitude, longitude)
    // console.log(res)
//     return res

// })
app.listen(port, () => {
    console.log('server started at port: '+port);
})

module.exports = app;

// app.get('/api/v1.0.0/searchutil/:keyword', async function (req, res) {
//     console.log(`\nSearch-utilities Call: ${req.params.keyword}`);
//     let origRes = await outerAPI.getAutocomplete(req.params.keyword, req.params.keyword, req.params.keyword);
//     let msg = `${req.params.keyword} Search-utilities finished at ${Date()}\nLength of response: ${origRes.length}`;
//     console.log(msg);
//     return res.send(origRes);
 
// })






// const baseurl = 'https://api.yelp.com/v3/businesses/search/'
// const apikey = 'eFjfsnyNaDucwiclVODe6g8o8EPqpPqYV8w6FEoVcRqr47zd_knY4hJOqcnpOvH5M9WqAEoEuMKtM_VKUUTWxK6RTvE3jgrgasFzmfhHD61ZV2LniP8lxbvDBcQoY3Yx'



// axios.get(`${baseurl}`, config)
//     .then(function(response){
//         console.log(response.data)
// })

// app.get('/api/users', function(req, res) {
//     const user_id = req.query.id;
//     const token = req.query.token;
//     const geo = req.query.geo;




// app.get('/search', async (req, res) => {
    
//     keyword = req.query.keyword;
//     category = req.query.category;
//     distance = parseInt(req.query.distance);
//     latitude = req.query.latitude;
//     longitude = req.query.longitude;

//     const baseurl = 'https://api.yelp.com/v3/businesses/search'
//     const apikey = 'eFjfsnyNaDucwiclVODe6g8o8EPqpPqYV8w6FEoVcRqr47zd_knY4hJOqcnpOvH5M9WqAEoEuMKtM_VKUUTWxK6RTvE3jgrgasFzmfhHD61ZV2LniP8lxbvDBcQoY3Yx'
   
//     const config = {
//         headers:{
//             'Authorization': `Bearer ${apikey}`
//         },
//         params: {
//             'term' : keyword,
//             'latitude' : latitude,
//             'longitude' : longitude,
//             'categories' : category,
//             'radius' : distance
//         }
//     }    

//     const get_response = await axios(baseurl, config)
//     console.log(get_response.data)
    
// })





// axios.get('https://api.yelp.com/v3/businesses/search/', {
//     headers: { 
//         'Authorization': `Bearer ${apikey}`
//     },  
// params: {
//     term : 'pizza',
//     latitude : '34.0180592',
//     longitude : '-118.2972656',
//     categories : 'food',
//     radius : 16000
//   }
// })
// .then(function(response){
//     console.log(response.data)
// })





// Make a request for a user with a given ID
// axios.get('')
//     .then((response)
//         https://api.yelp.com/v3/businesses/search?keyword=biryani&category=food&distance=16093.44&latitude=34.0180592&longitude=-118.2972656
//     )
  




// axios.get(`${baseurl}`, config)
//     .then((response) => {
//         console.log(response.data)
//     })


// url = "https://api.yelp.com/v3/businesses/search"
// key = "eFjfsnyNaDucwiclVODe6g8o8EPqpPqYV8w6FEoVcRqr47zd_knY4hJOqcnpOvH5M9WqAEoEuMKtM_VKUUTWxK6RTvE3jgrgasFzmfhHD61ZV2LniP8lxbvDBcQoY3Yx"

// headers = {
//     'Authorization' : 'Bearer %s' % key
// }

// parameters = {
//     'term' : keyword,
//     'latitude' : latitude,
//     'longitude' : longitude,
//     'categories' : category,
//     'radius' : distance
// }

// parameters = {
//     'term' : 'pizza',
//     'latitude' : '40.73',
//     'longitude' : '-73.99',
//     'categories' : 'food',
//     'radius' : 16000
// }

// response = axios.get(url, headers = headers, params = parameters)

// console.log(response)

// content = response.content

// console.log(content)




//adding middleware - cors
// app.use(cors());

//using body-parser
// app.use(bodyparser.json());

//serve static files
// app.use(express.static(path.join(__dirname, 'public')))
