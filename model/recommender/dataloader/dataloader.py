import ast
import os

import mysql.connector
import pandas as pd
from dotenv import load_dotenv

load_dotenv()


def main():
    full_data = get_data()
    df = pd.DataFrame(full_data, columns=["UserID"])
    df.to_parquet("dataset_user.parquet")


def get_data():
    config = ast.literal_eval(os.getenv("config"))
    db = mysql.connector.connect(**config)
    cursor = db.cursor()
    cursor.execute("SELECT UserID FROM Artjuna.account;")
    full_data = cursor.fetchall()
    return full_data

def test_parquet():
    df = pd.read_parquet("https://drive.google.com/uc?export=download&id=16kPOq9A0FKf6xfpmoguGnAwZjVoeh-kV")
    print(df)


if __name__ == "__main__":
    main()
    # test_parquet()
