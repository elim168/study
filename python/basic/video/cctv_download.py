# encoding=utf-8
import requests
from bs4 import BeautifulSoup
import re
import os
from moviepy.editor import *


class Cctv():

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


    def getEachDLUrl(self, url):
        response = self.openUrl(url)
        html = response.text
        # soup = BeautifulSoup(html, 'html.parser')
        match = re.search(r'guid = "(\w+?)";', html)
        pid = match.group(1)
        print('pid=' + pid)
        # print(pid)
        link = "http://vdn.apps.cntv.cn/api/getHttpVideoInfo.do?pid=%s&tz=%s&from=%s&url=%s&idl=%s&idlr=%s&modifyed=%s" % (
        pid, '-8', '000news', url, '32', '32', 'false')

        # 获取每一个片段的下载地址

        return self.getDLList(link)

    # 获取视频的每一个片段的下载链接
    def getDLList(self, link):
        """
        Get the download address for each episode of the TV play
        :return:ownload address list
        """
        # links = self.getEachDLUrl()
        # links = ["http://vdn.apps.cntv.cn/api/getHttpVideoInfo.do?pid=59381a0e55404cf5b101f7d3bcad2da8&tz=-8&from=000news&url=http://tv.cctv.com/2014/07/15/VIDE1405435161521590.shtml&idl=32&idlr=32&modifyed=false"]
        dl_urls = []
        response = self.openUrl(link)
        # html = response.content.decode('utf-8')
        json_data = response.json()
        print(json_data)
        validChapterNum = json_data['video']['validChapterNum']
        # dl_list = json_data['video']['chapters4']
        dl_list = json_data['video']['chapters' + str(validChapterNum)]
        for each in range(len(dl_list)):
            downloadurl = dl_list[each]['url']
            dl_urls.append(downloadurl)
        # 只返回前两个
        return dl_urls


    def download(self, url, name):
        dl_urls = self.getEachDLUrl(url)
        if not os.path.exists(name):
            os.mkdir(name)
        clips = []
        for i, url in enumerate(dl_urls):
            print('正在下载%s第%d段，URL=%s' % (name, i+1, url))
            file_name = '{0}/{1}.mp4'.format(name, i)
            with open(file_name, 'wb') as f:
                response = self.openUrl(url)
                for r in response:
                    f.write(r)
            clip = VideoFileClip(file_name)
            clips.append(clip)
        result_video = concatenate_videoclips(clips)
        result_video.write_videofile('%s.mp4' % (name))
        # 删除创建的临时文件
        # files = os.listdir(name)
        # for file in files:
        #     print(file)
        #     os.remove(os.path.join(name, file))
        # os.rmdir(name)


if __name__ == "__main__":
    cctv = Cctv()
    url = 'https://tv.cctv.com/2009/11/11/VIDE1355603184812672.shtml?spm=C55953877151.PuvgIQ6NQbQd.0.0'
    video_name = '2001赵本山-卖拐'
    cctv.download(url, video_name)