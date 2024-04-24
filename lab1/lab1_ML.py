import pandas as pd
from sklearn.preprocessing import MinMaxScaler, StandardScaler

df = pd.read_csv('train.csv')
pd.set_option("display.max_columns", None)
print(df)

#print("Missing values\n")
missing_values = df.isnull().sum
print(missing_values)

df["Age"].fillna(df["Age"].median)
df["HomePlanet"].fillna(df["HomePlanet"].fillna(df["HomePlanet"].mode()[0]))

print(df.isnull().sum())

cols = ["RoomService", "FoodCourt", "ShoppingMall", "Spa", "VRDeck"]
scaler = MinMaxScaler()  # или StandardScaler()
df[cols] = scaler.fit_transform(df[cols])
print(df.head())

df = pd.get_dummies(df, columns=["HomePlanet"], drop_first=True)
print("-------Final-------")
print(df)