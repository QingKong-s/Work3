import joblib

def get_predict(data):
    # 通过决策树 预测训练效果
    # print(data)
    data = tmp_modify(data)
    action_id = data['action_id']
    path = '../decision_trees/decision_tree_model_'
    suf = '.pkl'
    print(action_id)
    # 提取特征
    x = [
        data['age'],
        data['height'],
        data['weight'],
        data['history'],
        data['sex'],
        data['action_weight'],
        data['action_account']
    ]
    # 准备输入数据
    X_new = [x]
    try:
        # 加载模型
        clf_loaded = joblib.load(path + str(action_id) + suf)
        print(f"模型已从 decision_tree_model{action_id}.pkl 加载")
        # 进行预测
        prediction = clf_loaded.predict(X_new)
        return prediction
    except FileNotFoundError:
        print(f"模型文件 decision_tree_model{action_id}.pkl 未找到")
        return None
    except Exception as e:
        print(f"加载模型时发生错误: {e}")
        return None

def create_message(data):
    # 根据训练效果 返回讯息
    prediction = []
    print(data)
    for i in data:
        print(i)
        prediction.append(get_predict(i)[0])
    message = ""
    message_easy = "第"
    arr_easy = []
    message_hard = "第"
    arr_hard = []
    for i in range(len(prediction)):
        if prediction[i] == 0:arr_easy.append(i + 1)
        elif prediction[i] == 2:arr_hard.append(i + 1)
    if(len(arr_easy)):
        for i in len(arr_easy):
            if(i != 0):
                message_easy += " , "
            message_easy += str(arr_easy[i])
        message_easy += "项动作可能锻炼强度不是很大。"
        message += message_easy
    if (len(arr_hard)):
        for i in len(arr_hard):
            if (i != 0):
                message_hard += " , "
            message_hard += str(arr_easy[i])
        message_hard += "项动作可能对您来说有点难度，请注意。"
        message += message_hard
    message = more_action(message,prediction)
    if len(message) == 0:
        message = "您的计划完美无缺，太棒了！"

    return message


def tmp_modify(data): # 因为数据不够 只生成了一颗决策树
    data['action_id'] = 1
    return data

def more_action(message,prediction):
    # 推荐其他动作
    # load_graph() 导入运动，部位关系图
    load_graph()


    return message

def load_graph():
    # 暂时没有对运动 部位建模

    return

tmp_data = {}
tmp_data['age'] = 21
tmp_data['action_id'] = 2
tmp_data['height'] = 170
tmp_data['weight'] = 140
tmp_data['history'] = 50
tmp_data['sex'] = 0
tmp_data['action_weight'] = 45
tmp_data['action_account'] = 8
tot = [tmp_data]
print(get_predict(tmp_data))
print(tot)
print(create_message(tot))