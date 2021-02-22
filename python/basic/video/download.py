import requests


def download_video(url):
    print('*' * 100)
    print(f'下载URL：{url}')
    headers = {
            'Referer': 'https://www.ixigua.com',
            'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36',
            'cookie': 'wafid=8b91d940-81ec-4620-af0f-f45d479a62c2; wafid.sig=BZgx1eD0aFGn25mL-y-SEh17cng; ttwid=6841106955945346564; ttwid.sig=glkPgElc0Yh0OEDyNL0P91fmbZg; xiguavideopcwebid=6841106955945346564; xiguavideopcwebid.sig=avM_v_QTwC7VqM26Yqde9eer3xA; _ga=GA1.2.1235075053.1592819342; SLARDAR_WEB_ID=fa1eb835-d608-4ade-850d-bc0409bd541f; _gid=GA1.2.303152420.1593089518; ixigua-a-s=1; Hm_lvt_db8ae92f7b33b6596893cdf8c004a1a2=1593094562,1593095154,1593098009,1593147688; Hm_lpvt_db8ae92f7b33b6596893cdf8c004a1a2=1593153331',
        }
    file_path = '/home/elim/abc.mp4'
    response = requests.get(url=url, headers=headers, stream=True)
    print(str(response))
    #content_size = int(response.headers["content-length"])  # 视频内容的总大小
    size = 0
    with open(file_path, "wb") as file:  # 非纯文本都以字节的方式写入
        for data in response.iter_content(chunk_size=1024):  # 循环写入
            file.write(data)  # 写入视频文件
            file.flush()  # 刷新缓存
            size += len(data)  # 叠加每次写入的大小
            # 打印下载进度
            # print("\r文件下载进度:%d%%(%0.2fMB/%0.2fMB)" % (
            #     float(size / content_size * 100), (size / 1024 / 1024),
            #     (content_size / 1024 / 1024)),
            #       end=" ")
    print()
    print(url)



if __name__ == '__main__':
    download_video('https://www.ixigua.com/embed?group_id=6639488752022454798')