# -*- coding: utf-8 -*-
from selenium import selenium
import unittest, time, re

class tc-google(unittest.TestCase):
    def setUp(self):
        self.verificationErrors = []
        self.selenium = selenium("localhost", 4444, "*chrome", "https://www.google.fr/")
        self.selenium.start()
    
    def test_tc-google(self):
        sel = self.selenium
        sel.open("/?gws_rd=ssl")
        sel.type("id=lst-ib", "hello")
        sel.click("id=lst-ib")
        sel.type("id=lst-ib", "toto")
    
    def tearDown(self):
        self.selenium.stop()
        self.assertEqual([], self.verificationErrors)

if __name__ == "__main__":
    unittest.main()
