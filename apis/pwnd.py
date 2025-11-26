import hashlib
import requests

def check_password(password: str):
    sha1 = hashlib.sha1(password.encode('utf-8')).hexdigest().upper()

    prefix = sha1[:5]
    suffix = sha1[5:]

    url = f"https://api.pwnedpasswords.com/range/{prefix}"
    response = requests.get(url)

    if response.status_code != 200:
        raise RuntimeError("BŁąd polaczenia z api")

    hashes = response.text.split("\n")

    for line in hashes:
        hash_suffix, count = line.split(":")
        if hash_suffix == suffix:
            return int(count)

    return 0


if __name__ == "__main__":
    password = input("podaj hasło: ")

    count = check_password(password)

    if count > 0:
        print(f"Hasło wyciekło {count} razy")
    else:
        print("Nie odnaleziono hasła w żadnych wyciekach.")
