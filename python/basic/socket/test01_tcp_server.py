import socket

server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
server_socket.bind(('', 8888))  # localhost:8888
server_socket.listen()
client_socket, client_address = server_socket.accept()
while True:
    data = client_socket.recv(1024)
    data = data.decode()
    print('receive data[%s] from client[%s]' % (data, client_address))
    client_socket.send(('response---' + data).encode())
    if data == 'bye':
        break
client_socket.close()
server_socket.close()


