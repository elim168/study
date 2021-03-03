import requests
from bs4 import BeautifulSoup
import re
# import senddetail
import sys
import pandas as pd

host_ip = ''


def Spider(keywords, page, type):
    url = "https://search.bilibili.com/all?keyword=" + keywords + "&page=" + str(page)  # 构建url
    if type == 1:
        url = "https://search.cctv.com/ifsearch.php?page=" + str(
            page) + "&qtext=" + keywords + "&sort=relevance&type=video"
    headers = {
        'Cookie': 'main_confirmation=C/Mb1jdk4KPIlFgveREEr4jMzGMI2VRMQJz7MIvUIoc='
    }
    playload = {}
    response = requests.request("GET", url, headers=headers, data=playload)  # 构造request

    # print(response)
    # print('------')
    html_content = response.text  # 爬下来整个网页，带网页标签的，下面通过BeautifulSoup进行数据清理
    # print(html_content)

    soup = BeautifulSoup(html_content, 'html.parser')

    item = soup.find_all(class_='video-item matrix')

    if type == 1:
        html_content = response.json()
        item = html_content['list']

    # 当爬取不到视频时结束
    if not item:
        print("爬取结束")
        sys.exit()

    keyword_list = []
    title_list = []
    url_list = []
    vid_list = []

    count = 0
    for i in item:
        if type == 1:
            # print(item[count])

            title = item[count]['all_title']
            # print('---',title)
            href = item[count]['urllink']
            href = href.split(','[0])
            href = href[0]

            # print('***',href)
            count = count + 1
        else:
            i = str(i).replace(' ', '')
            # 正则匹配视频标题
            title = re.findall("title.*?>", i)
            title = re.findall("\".*?\"", str(title))[0][1:-1]
            # 视频链接
            href = re.findall("class=\"title\".*?>", i)
            href = re.findall("//.*?\"", str(href))[0][2:-13]
        # 观看
        # UP主
        # 以上tag暂不添加需求
        print(href)
        detail = {
            "keyword": keywords,
            "durations": "1",
            "frame": 60,
            "title": title,
            "href": href
        }
        jsr = senddetail.SendVideoMessage(detail)
        if (jsr['status'] == 200):
            keyword_list.append(keywords)
            title_list.append(title)
            url_list.append(href)
            vid_list.append(jsr['vid'])

    db = 'test.csv'
    df = pd.read_csv(db, index_col=0)
    new = pd.DataFrame({

        'keywrod': keyword_list,
        'title': title_list,
        'url': url_list,
        'vid': vid_list,

    })

    df1 = df.append(new, ignore_index=True)  # ignore_index=True,表示不按原来的索引，从0开始自动递增
    df1.to_csv('test.csv')
    if type == 1:
        print("央视网" + keywords + "第" + str(page) + "页" + "爬取结束")
    else:
        print("B站" + keywords + '第' + str(page) + '页爬取结束')


Spider('春晚', 1, type=1)