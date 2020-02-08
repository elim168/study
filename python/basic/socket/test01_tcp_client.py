import socket

client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
client_socket.connect(('localhost', 8888))
client_socket.send('hello server'.encode())
data = client_socket.recv(1024)
print('receive data from server is: %s' % data.decode())
client_socket.send('bye'.encode())
data = client_socket.recv(1024)
print('receive data from server is: %s' % data.decode())
client_socket.close()

