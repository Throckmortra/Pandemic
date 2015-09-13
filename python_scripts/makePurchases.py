#!/usr/bin/python

import random
import requests
import json

apiKey = 'd566c0e9c969eb4c02760ef8ecbcabf0'

url = 'http://api.reimaginebanking.com/accounts/55e94a6cf8d8770528e6170c/purchases?key={}'.format(apiKey)

with open('merchantIDs.txt') as f:
    content = f.readlines()

for i in range(0,6000):
    id = random.randrange(0,len(content))
    payload = {
      "merchant_id": content[id].rstrip(),
      "medium": "balance",
      "amount": 0
    }    

    req = requests.post(
        url,
        data=json.dumps(payload),
        headers={'content-type':'application/json'}
        )
    print (req.text)
    print (content[id])
    print (req.status_code)
