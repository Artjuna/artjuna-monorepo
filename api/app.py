import os
import sys

arg = sys.argv
if len(arg) == 1:
    os.system("uvicorn ml_api.api_core:app")
elif arg[1] in ["-r", "--reload"]:
    os.system("uvicorn ml_api.api_core:app --reload")
else:
    print("Invalid argument")
