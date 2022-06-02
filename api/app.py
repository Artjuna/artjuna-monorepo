import os
import sys

arg = sys.argv
if len(arg) == 1:
    os.system("uvicorn ml_api.style_transfer_api:app")
elif arg[1] in ["-r", "--reload"]:
    os.system("uvicorn ml_api.style_transfer_api:app --reload")
else:
    print("Invalid argument")
