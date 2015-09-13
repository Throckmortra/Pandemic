#!/usr/bin/python

import random
import requests
import json

apiKey = 'd566c0e9c969eb4c02760ef8ecbcabf0'

lat_list = []
lng_list = []

url = 'http://api.reimaginebanking.com/merchants/?key={}'.format(apiKey)



for i in range(0, 600):
	lat = 40 + random.uniform(-1, 1)
	lng = -100 + random.uniform(-2, 2)
	print (lat, lng)
	lat_list.append(lat)
	lng_list.append(lng)
	payload = {
		"name": "pharmacy",
		"geocode": {
			"lat": lat,
			"lng": lng
		}
	}
	req = requests.post(
		url,
		data=json.dumps(payload),
		headers={'content-type':'application/json'}
		)
	print (req.text)
	print (req.status_code)
	
