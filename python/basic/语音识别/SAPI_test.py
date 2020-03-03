# linux不支持

from comtypes.client import CreateObject

engine = CreateObject('SAPI.SpVoice')
print(dir(engine))

