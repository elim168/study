import socket
import threading

client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
client_socket.connect(('localhost', 8899))
quit_flag = False


def read_data(client_socket):
    while not quit_flag:
        try:
            recv_data = client_socket.recv(1024)
            recv_data = recv_data.decode()
            print(recv_data)
        except Exception as e:
            print(e)


def send_data(client_socket):
    while True:
        message = input('>')
        client_socket.send(message.encode())
        if message == 'quit':
            client_socket.close()
            global quit_flag
            quit_flag = True
            break


# 启动两个线程，这样就可以边读边写
t1 = threading.Thread(target=read_data, args=(client_socket,))
t2 = threading.Thread(target=send_data, args=(client_socket,))
t1.start()
t2.start()

