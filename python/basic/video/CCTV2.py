# encoding=utf-8
import requests
from bs4 import BeautifulSoup
import re
import os
# from aria2rpc import rpc_addUri
import pyaria2




class Cntv():

    def openUrl(self, url):
        """
        This method is used to open a web site
        :param url:Web site to request
        :return:Requested object
        """
        header = {
            "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36"
        }
        response = requests.get(url, header)
        return response
        # pass

    def getEachEpisodeUrl(self):
        """
        Get the address of each episode of the TV play
        :return:urls lists
        """
        urls = []
        # response = requests.get(self.url)
        url = "http://tv.cctv.com/2014/07/07/VIDA1404730290373811.shtml"
        response = self.openUrl(url)
        html = response.content.decode('utf-8')
        soup = BeautifulSoup(html, 'html.parser')
        title = soup.select(".text_mod h3")
        print(title[0].text)
        episodes = soup.select('.img a')
        # print(episodes)
        for each in range(1, len(episodes), 3):
            print(episodes[each]['title'], "link:" + episodes[each]['href'])
            urls.append(episodes[each]['href'])
        print("Get Each Episode Url Come Over !!!")
        return urls

    def getEachDLUrl(self):
        urls = self.getEachEpisodeUrl()
        links = []
        for num, url in enumerate(urls):
            response = self.openUrl(url)
            html = response.text
            # soup = BeautifulSoup(html, 'html.parser')
            match = re.search(r'guid = "(\w+?)";', html)
            pid = match.group(1)
            print('pid=' + pid)
            # print(pid)
            link = "http://vdn.apps.cntv.cn/api/getHttpVideoInfo.do?pid=%s&tz=%s&from=%s&url=%s&idl=%s&idlr=%s&modifyed=%s" % (
            pid, '-8', '000news', url, '32', '32', 'false')
            links.append(link)
            print("获取第%d集" % (num))
            # print(urls)
        return links

    def getDLList(self):
        """
        Get the download address for each episode of the TV play
        :return:ownload address list
        """
        links = self.getEachDLUrl()
        # links = ["http://vdn.apps.cntv.cn/api/getHttpVideoInfo.do?pid=59381a0e55404cf5b101f7d3bcad2da8&tz=-8&from=000news&url=http://tv.cctv.com/2014/07/15/VIDE1405435161521590.shtml&idl=32&idlr=32&modifyed=false"]
        dl_urls = []
        for link in links:
            dl_url = []
            response = self.openUrl(link)
            # html = response.content.decode('utf-8')
            dl_list = response.json()['video']['chapters4']
            for each in range(len(dl_list)):
                downloadurl = dl_list[each]['url']
                dl_url.append(downloadurl)
                print(downloadurl)
            dl_urls.append(dl_url)
        # 只返回前两个
        return dl_urls[:2]

    def _add_aria2_task(self, url, name):
        """
        :param url:download url
        :param name:dowmload tv name
        :return:
        """
        try:
            result = pyaria2.Aria2RPC().addUri(uris=[url], options=dict(dir='/tmp'))
            return result
        except Exception as e:
            print(e)
            return None


# response.json()['video']['lowChapters'][0]['url']
# response.json()['video']['chapters4'][0]['url']
"""
    def dlTv(self):

        dl_urls_list = self.getDLList()
        if os.path.exists("tv_list") == False:
            os.mkdir("tv_list")
        os.chdir("tv_list")
        for dl_urls in dl_urls_list:
            for dl_url in dl_urls:
                print("download" + dl_url)
                # response = self.openUrl(dl_url)
                # with open("first.mp4",'ab') as tl:
                #     tl.write(response.content)
            print("-"*20)
"""
if __name__ == "__main__":
    cm = Cntv()
    # cm.getUrl()
    # cm.openUrl()

    lists = cm.getDLList()
    for num, list in enumerate(lists):
        for i, url in enumerate(list):
            # cm._add_aria2_task(url, str(num + 1) + '_' + str(i + 1) + '.mp4')
            response = cm.openUrl(url)
            with open('t_{0}_{1}.mp4'.format(num, i), 'wb') as f:
                for r in response:
                    f.write(r)