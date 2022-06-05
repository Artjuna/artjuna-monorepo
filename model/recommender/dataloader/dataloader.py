import ast
import os

import mysql.connector
import pandas as pd
from dotenv import load_dotenv

load_dotenv()


def main():
    full_data = get_data()
    df = pd.DataFrame(full_data, columns=["UserID", "ProductID"])
    df.to_parquet("dataset.parquet")


def get_data():
    config = ast.literal_eval(os.getenv("config"))
    db = mysql.connector.connect(**config)
    cursor = db.cursor()
    cursor.execute("SELECT UserID,ProductID FROM Artjuna.productSeen;")
    full_data = cursor.fetchall()
    return full_data


if __name__ == "__main__":
    main()
