import requests
import xml.etree.ElementTree as ET

URL = "https://thehackernews.com/feeds/posts/default?alt=rss"

response = requests.get(URL)
response.raise_for_status()

xml_data = response.text

root = ET.fromstring(xml_data)

ns = {"atom": "http://www.w3.org/2005/Atom"}

for entry in root.findall("channel/item"):
    title = entry.find("title").text if entry.find("title") is not None else "Brak tytułu"
    description = entry.find("description").text if entry.find("description") is not None else "Brak opisu"

    print("Title:", title)
    print("Description:", description)
    print("-" * 60)
