import pandas as pd
import random 

# 创建示例数据
data = {
    'age': [],
    'height': [],
    'weight': [],
    'history': [],
    'sex': [],
    'action_wight': [],
    'action_cnt':[],
    'score':[]
}
mean = 30
std_dev = 20
noral_random = random.gauss(mean, std_dev)
for i in range (1,50,1):
    # tmp = []
    data['age'].append(random.randint(18,55))#age
    mean = 170
    std_dev = 30
    noral_random = random.gauss(mean, std_dev)
    data['height'].append(random.randint(130,210))#height
    mean = 150
    std_dev = 40
    noral_random = random.gauss(mean, std_dev)
    data['weight'].append(random.randint(50,200))#weight
    mean = 50
    std_dev = 20
    noral_random = random.gauss(mean, std_dev)
    history = random.randint(20,110)
    data['history'].append(history)#history
    data['sex'].append(random.randint(0,1))#sex 0 男性 1 女性
    mean = history - 10
    std_dev = 10
    noral_random = random.gauss(mean, std_dev)
    data['action_wight'].append(random.randint(max(history - 30,10),history + 10))#action_wihgt
    mean = 5
    std_dev = 1
    data['action_cnt'].append(random.randint(3,8))#action_cnt
    data['score'].append(0)

# 创建DataFrame
df = pd.DataFrame(data)

# 保存为CSV文件
df.to_csv('./GUI/project_1/data/test.csv', index=False)

# 打印数据框以确认内容
print(df)