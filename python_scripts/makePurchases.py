#!/usr/bin/python

import random
import requests
import json

# apiKey = 'd566c0e9c969eb4c02760ef8ecbcabf0'
apiKey = '10a587f5784c9e280d1e6a42281f87fa'

url = 'http://api.reimaginebanking.com/accounts/55e94a6cf8d8770528e61758/purchases?key={}'.format(apiKey)

with open('merchantIDs.txt') as f:
    content = f.readlines()

for i in range(0,100):
    id = random.randrange(0,len(content))
    day = random.randrange(1,31)
    payload = {
      "merchant_id": content[id].rstrip(),
      "medium": "balance",
      "purchase_date": "9/{}/2015".format(day),
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
