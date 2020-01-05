class Bag:
    def __init__(self):
        self.data = []

    def add(self, x):
        self.data.append(x)

    def addTwice(self, x):
        self.add(x)
        self.add(x)


bag = Bag()
bag.add(1)
bag.add(10)
bag.addTwice(100)
print(bag.data)
