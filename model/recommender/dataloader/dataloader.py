import pandas as pd

DATA_URL = "D:/ratings.csv"
df = pd.read_csv(DATA_URL)
ratings = tf.data.Dataset.from_tensor_slices(dict(df)).map(lambda x: {
    "user_id": str(x["user_id"]),
    "item_id": str(x["item_id"]),
    "rating": float(x["rating"])
})
