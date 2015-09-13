#!/usr/bin/python

import random
#import requests
import json
from pprint import pprint
#apiKey = 'd566c0e9c969eb4c02760ef8ecbcabf0'

#url = 'http://api.reimaginebanking.com/accounts/55e94a6cf8d8770528e6170c/purchases?key={}'.format(apiKey)

with open('merchantIDs.txt') as f:
	content = f.readlines()
with open('purchases.json') as data_file:
	data = json.load(data_file)
with open('merchants.json') as data_file0:
	merchantdata = json.load(data_file0)
elements=[]
elements.append([])
elements.append([])
elements.append([])
elements.append([])
#id, weight, lat, long
for i in range(0,len(content)):
	elements[0].append(content[i])
for i in range(0,len(content)):
	elements[1].append(0)	
for i in range(0,len(content)):
        elements[2].append(0)
for i in range(0,len(content)):
        elements[3].append(0)

for i in data:
	for x in range(0,len(elements[0])):
		if (i['merchant_id'] == elements[0][x].rstrip()):
			elements[1][x]+=1
for i in merchantdata:
	for x in range(0,len(elements[0])-1):
		if (i['_id'] == elements[0][x].rstrip()):
			elements[2][x]=i['geocode']['lat']
			elements[3][x]=i['geocode']['lng']
#content.append([])

#for i in range(0,len(content)):
#	content[1].append("0")

#print(content[])
for i in range(0,len(elements[0])):
	print(elements[0][i].rstrip(), elements[1][i], elements[2][i], elements[3][i])


