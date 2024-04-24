from sklearn.datasets import load_iris
from sklearn.model_selection import train_test_split
from sklearn.metrics import accuracy_score, f1_score, recall_score, precision_score, confusion_matrix
from sklearn.linear_model import LogisticRegression, LinearRegression
from sklearn.metrics import confusion_matrix
import seaborn as sns
import matplotlib.pyplot as plt
import pandas as pd

iris = load_iris()
df = pd.DataFrame(iris.data, columns=iris.feature_names)
df['target'] = iris.target
print(df.head())

X = df.drop(columns = ['target'])
Y = df['target']

X_train, X_test, y_train, y_test = train_test_split(X,Y, test_size = 0.2, random_state = 42)

print("Logistic Regression:")
log_reg = LogisticRegression(max_iter=200)
log_reg.fit(X_train, y_train)
y_pred_log = log_reg.predict(X_test)

accuracy_log = accuracy_score(y_test, y_pred_log)
f1_log = f1_score(y_test, y_pred_log, average="weighted")
recall_log = recall_score(y_test, y_pred_log, average="weighted")
precision_log = precision_score(y_test, y_pred_log, average="weighted")

print(f"Accuracy: {accuracy_log:.2f}")
print(f"F1 Score: {f1_log:.2f}")
print(f"Recall: {recall_log:.2f}")
print(f"Precision: {precision_log:.2f}")

cm_log = confusion_matrix(y_test, y_pred_log)
sns.heatmap(cm_log, annot=True, fmt='d', cmap="Purples", xticklabels=iris.target_names, yticklabels=iris.target_names)
plt.title("Confusion Matrix: Logistic Regression")
plt.xlabel("Predicted")
plt.ylabel("True")
plt.show()

print("Linear Regression:")
lin_reg = LinearRegression()
lin_reg.fit(X_train, y_train)
y_pred_lin = lin_reg.predict(X_test)

y_pred_lin_rounded = [round(pred) for pred in y_pred_lin]

accuracy_lin = accuracy_score(y_test, y_pred_lin_rounded)
f1_lin = f1_score(y_test, y_pred_lin_rounded, average="weighted")
recall_lin = recall_score(y_test, y_pred_lin_rounded, average="weighted")
precision_lin = precision_score(y_test, y_pred_lin_rounded, average="weighted")

print(f"Accuracy: {accuracy_lin:.2f}")
print(f"F1 Score: {f1_lin:.2f}")
print(f"Recall: {recall_lin:.2f}")
print(f"Precision: {precision_lin:.2f}")

cm_lin = confusion_matrix(y_test, y_pred_lin_rounded)
sns.heatmap(cm_lin, annot=True, fmt='d', cmap="Greens", xticklabels=iris.target_names, yticklabels=iris.target_names)
plt.title("Confusion Matrix: Linear Regression")
plt.xlabel("Predicted")
plt.ylabel("True")
plt.show()
