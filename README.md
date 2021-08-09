# Sample distance calculator API

## Shortcomings:
- No in-built E2E testing
- No automatic/formal API documentation
- Poor exception propagation and error reporting
- Service tests are wholly unnecessary as they have very little logic to test, and only serve as an example

## API

### POST: /calculate/{operation}
`operation` - operation to be performed
#### Body
`distance1` - first `Distance`<br>
`distance2` - second `Distance`<br>
`resultUnit` - `Unit` in which the result is to be returned

##### Distance
`amount` - amount of unit in the distance<br>
`unit` - `Unit` of distance<br>
`exponent`: optional - exponent of the unit in `^x` format, defaults to 1<br>
##### Unit
One of `METER`:`m`, `NAUTICAL_MILE`:`nm`, `FEET`:`ft`
#### Example:
`POST: /calculate/add`
```
{
    "distance1": {
        "amount": 0,
        "unit": "ft",
    },
    "distance2": {
        "amount": 1,
        "unit": "nm",
        "exponent": 1
    },
    "resultUnit": "meter"
}
```

### GET: /calculate/{request}
`request` - formatted string representing the calculation<br>
`{Distance}{Operator}{Distance}={Unit}`
##### Distance
`{amount}{unit}{exponent}`<br>
`amount` - amount of unit in the distance<br>
`unit` - `Unit` of distance<br>
`exponent`: optional - exponent of the unit in `^x` format, defaults to 1<br>
##### Operator
One of `+`, `-`, `|` - division, `*`
##### Unit
One of `METER`:`m`, `NAUTICAL_MILE`:`nm`, `FEET`:`ft`

#### Example
`GET: /calculate/1.2m^1+4ft=nm`