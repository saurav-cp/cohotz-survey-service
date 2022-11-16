## Survey Service

## Sample Survey payload
```
{
  "name": "Survey 1",
  "description": "Survey 1",
  "publisher": "saurav@cultrplus.com",
  "comment": "some comment",
  "tags": [
    "survey"
  ],
  "startDate": "2022-11-12T09:11:50.029Z",
  "endDate": "2022-11-15T09:11:50.029Z",
  "status": "Draft",
  "participantSource": "MANUAL",
  "block": "ONBOARDING",
  "participants": [
    "saurav@cultrplus.com"
  ],
  "reminderFrequencyInDays": 7,
  "smartSkip": true,
  "type": "CULTURE_ENGINE_STATIC"
}
```

## Sample response
```
[
  {
    "comment": "Some Comment",
    "skipped": false,
    "type": "SINGLE_SELECT",
    "question_code": "COMMUNICATION_Q5",
    "selections": [
      0
    ]
  },
  {
    "comment": "Some Comment",
    "skipped": false,
    "type": "SINGLE_SELECT",
    "question_code": "COMMUNICATION_Q1",
    "selections": [
      1
    ]
  },
  {
    "comment": "Some Comment",
    "skipped": false,
    "type": "SINGLE_SELECT",
    "question_code": "LEADERSHIP_Q4",
    "selections": [
      1
    ]
  },
  {
    "comment": "Some Comment",
    "skipped": false,
    "type": "SINGLE_SELECT",
    "question_code": "COMMUNICATION_Q3",
    "selections": [
      1
    ]
  },
  {
    "comment": "Some Comment",
    "skipped": false,
    "type": "SINGLE_SELECT",
    "question_code": "LEADERSHIP_Q6",
    "selections": [
      1
    ]
  },
  {
    "comment": "Some Comment",
    "skipped": false,
    "type": "SINGLE_SELECT",
    "question_code": "EMPOWERMENT_Q5",
    "selections": [
      1
    ]
  },
  {
    "comment": "Some Comment",
    "skipped": false,
    "type": "SINGLE_SELECT",
    "question_code": "FULFILLING_WORK_RELATIONSHIPS_Q4",
    "selections": [
      1
    ]
  },
  {
    "comment": "Some Comment",
    "skipped": false,
    "type": "SINGLE_SELECT",
    "question_code": "WELL_BEING_Q5",
    "selections": [
      1
    ]
  }

]
```