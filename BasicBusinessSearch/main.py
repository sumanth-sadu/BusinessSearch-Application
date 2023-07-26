from flask import Flask, request, jsonify, send_from_directory
import requests, json
from flask_cors import CORS


app = Flask(__name__, static_url_path='/static')
CORS(app)

@app.route('/')
def home():
    return app.send_static_file('index.html')

@app.route('/search', methods=['GET'])
def search():
    args = request.args
    keyword = args.get('keyword')
    category = args.get('category')
    distance = int(float(args.get('distance', 16000)))
    latitude = args.get('latitude')
    longitude = args.get('longitude')

    url = "https://api.yelp.com/v3/businesses/search"
    key = "eFjfsnyNaDucwiclVODe6g8o8EPqpPqYV8w6FEoVcRqr47zd_knY4hJOqcnpOvH5M9WqAEoEuMKtM_VKUUTWxK6RTvE3jgrgasFzmfhHD61ZV2LniP8lxbvDBcQoY3Yx"
    
    headers = {
        'Authorization' : 'Bearer %s' % key
    }

    parameters = {
        'term' : keyword,
        'latitude' : latitude,
        'longitude' : longitude,
        'categories' : category,
        'radius' : distance
    }

    response = requests.get(url, headers = headers, params = parameters)

    content = response.content

    return content


@app.route('/moreinfo', methods=['GET'])
def moreinfo():
    args = request.args
    id = args.get('id')

    url = "https://api.yelp.com/v3/businesses/" + id
    key = "eFjfsnyNaDucwiclVODe6g8o8EPqpPqYV8w6FEoVcRqr47zd_knY4hJOqcnpOvH5M9WqAEoEuMKtM_VKUUTWxK6RTvE3jgrgasFzmfhHD61ZV2LniP8lxbvDBcQoY3Yx"
    headers = {
        'Authorization' : 'Bearer %s' % key
    }

    response = requests.get(url, headers = headers)

    content = response.content

    return content


if __name__ == '__main__':
  app.run(debug=True)