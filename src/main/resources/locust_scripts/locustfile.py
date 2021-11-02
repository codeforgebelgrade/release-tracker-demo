import time
from locust import HttpUser, task, between

class MyUser(HttpUser):

    @task()
    def postSvcRequest(self):
        self.client.post("/releases", json={"releaseName":"Release X", "releaseDescription":"test description", "releaseStatus": "Created", "releaseDate": "2021-10-10"})
