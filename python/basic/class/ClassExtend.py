class BaseClass:
    def baseFunc1(self):
        print('Base Func1')

    def baseFunc2(self):
        print('Base Func2')

class SubClass(BaseClass):
    def subFunc1(self):
        print('Sub Func1')

    def baseFunc2(self):
        print('Sub override baseFunc2')
        print('调用父类的方法------', BaseClass.baseFunc2(self))

sub = SubClass()
sub.baseFunc1()
sub.baseFunc2()
sub.subFunc1()

print('sub is instance BaseClass =', isinstance(sub, BaseClass))
print('sub is instance SubClass =', isinstance(sub, SubClass))

print('SubClass issubclass of BaseClass =', issubclass(sub.__class__, BaseClass))
print(sub.__class__)