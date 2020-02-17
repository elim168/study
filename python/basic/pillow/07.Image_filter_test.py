from PIL import Image, ImageFilter

image = Image.open('flower_01.jpg')
# image.thumbnail((200, 1000))
w, h = image.size
print(image.size)
out_image = Image.new('RGB', (2 * w, h))
out_image.paste(image, (0, 0))
# filters = [ImageFilter.FIND_EDGES, ImageFilter.BLUR, ImageFilter.CONTOUR, ImageFilter.DETAIL,
#            ImageFilter.EDGE_ENHANCE, ImageFilter.EDGE_ENHANCE_MORE, ImageFilter.EMBOSS, ImageFilter.GaussianBlur,
#            ImageFilter.SHARPEN, ImageFilter.SMOOTH]
# for f in filters:
#     filtered_image = image.filter(f)
#     out_image.paste(filtered_image, (w, 0))
#     out_image.show()
filtered_image = image.filter(ImageFilter.FIND_EDGES)
# filtered_image = image.filter(ImageFilter.CONTOUR)
filtered_image = filtered_image.filter(ImageFilter.DETAIL)
# filtered_image.save('elim_filtered.jpg')
out_image.paste(filtered_image, (w, 0))
out_image.show()

