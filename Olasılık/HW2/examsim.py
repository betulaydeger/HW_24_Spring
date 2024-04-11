import numpy as np
import matplotlib.pyplot as plt
from scipy.special import comb as combination, factorial
from scipy.integrate import quad as integrate  # quad, belirli integral hesaplamak için kullanılır
import random as random 
"""
Not: The entire program takes about 2 minutes to run
Outputs:
Enter the M number: 400
b-) mtMor 400.0 :  0.999999595934386
c-) s:  0.9999999999997392
d-) news:  0.9974673287179181
e-) lt300:  5.771506806917515e-07
f-) p1m25: 0.5285234815199048
g-) b6080:  0.08814922171219003
"""
#---------------------------------------------------Helper Functions--------------------------------------------------------
# binom probability function
def binom_probability(n, k, p):
    return combination(n, k) * (p**k) * ((1-p)**(n-k))

# probability of correct, wrong, blank answer according to time
def probability_of_correct_answer(t):
    return 0.6 + (2*t-40)/1000

def probability_of_wrong_answer(t):
    return 0.2 + (30-t)/1000

def probability_of_blank_answer(t):
    return 1-probability_of_correct_answer(t)-probability_of_wrong_answer(t)

# probability of d correct and y wrong answers according to probabilities of correct, wrong and blank answers
def probability_of_d_correct_and_y_wrong_answers(n, d, y, p_correct, p_wrong, p_blank):
    return combination(n, d) * (p_correct**d) * combination(n-d, y) * (p_wrong**y) * (p_blank**(n-d-y))


# return point according to d and y
def point(d, y):
    if (d - y/4)*5 > 0:
        return (d - y/4)*5 
    else:
        return 0

# cdf of exponential distribution from a to b:
def cdf_of_exponential_distribution_from_a_to_b(a, b, lambda_rate):
    return integrate((lambda x: lambda_rate * np.e ** (-1*x*lambda_rate)), a, b)

# probability of exponential distribution
def probability_of_exponential_distribution(x,lambda_rate):
    return lambda_rate * np.e ** (-1*x*lambda_rate)

# erlang distribution
def erlang_distribution(n,lambda_rate,x):
    return (lambda_rate**n * x**(n-1) * np.e**(-1*lambda_rate*x)) / factorial(n-1)


#----------------------------------------------part a------------------------------------------------------
def save_png_of_probability_pistribution_of_correct_answer():
    n = 100
    x=np.arange(0,101)
    p = probability_of_correct_answer(200)
    probabilities = [binom_probability(n, xi, p) for xi in x]
    plt.xlabel('Number of Questions') # x eksenine etiket
    plt.ylabel('Probability of Correct Answer') # y eksenine etiket
    plt.title('Probability Mass Function of Fx (corrects)') # başlık
    plt.bar(x, probabilities, color='green')
    plt.savefig('pxpmf.png')
    plt.close()

def save_png_of_probability_pistribution_of_wrong_answer():
    n = 100
    x=np.arange(0,101)
    p = probability_of_wrong_answer(200)
    probabilities = [binom_probability(n, xi, p) for xi in x]
    plt.xlabel('Number of Questions') # x eksenine etiket
    plt.ylabel('Probability of Wrong Answer') # y eksenine etiket
    plt.title('Probability Mass Function of Fy (wrongs)') # başlık
    plt.bar(x,probabilities, color='red')
    plt.savefig('pypmf.png')
    plt.close()

def save_png_of_probability_pistribution_of_blank_answer():
    n = 100
    x=np.arange(0,101)
    p = probability_of_blank_answer(200)
    probabilities = [binom_probability(n, xi, p) for xi in x]
    plt.xlabel('Number of Questions') # x eksenine etiket
    plt.ylabel('Probability of Blank Answer') # y eksenine etiket
    plt.title('Probability Mass Function of Fz (blanks)') # başlık
    plt.bar(x,probabilities, color='gray')
    plt.savefig('pzpmf.png')
    plt.close()

#------------------------------------------part b--------------------------------------------------
def probability_of_getting_more_than_M_points(M):
    n = 100
    prob = 0.0
    correct=probability_of_correct_answer(200) 
    wrong=probability_of_wrong_answer(200)
    blank=probability_of_blank_answer(200)
    for d in range(0,n+1):
        for y in range(0,n-d+1):
            if point(d,y) > M:
                prob += probability_of_d_correct_and_y_wrong_answers(n, d, y, correct, wrong, blank)
    return prob 

#--------------------------------------------part c--------------------------------------------------
def probability_of_passing_exam_successfully():
    #conditional probability
    correct=probability_of_correct_answer(120)
    wrong=probability_of_wrong_answer(120)
    blank=probability_of_blank_answer(120)
    conditional_prob=0.0
    # ilk 31 sorudan 20 doğru 7 yanlış 4 boş yapma olasılığı
    for d in range(20,101):
        for y in range(7,101-d):
                conditional_prob += probability_of_d_correct_and_y_wrong_answers(31,20,7,correct,wrong,blank)*probability_of_d_correct_and_y_wrong_answers(69,d-20,y-7,correct,wrong,blank)
    prob = 0.0

    for d in range(20,101):
        for y in range(7,101-d):
            if point(d,y)>=200:
                prob += probability_of_d_correct_and_y_wrong_answers(31,20,7,correct,wrong,blank)*probability_of_d_correct_and_y_wrong_answers(69,d-20,y-7,correct,wrong,blank) 
    return prob/conditional_prob

#--------------------------------------------part d------------------------------------------------------
def probability_of_being_succesfull_with_8_blank_answers(M):
    #conditional probability


    p_correct=probability_of_correct_answer(200)
    p_wrong=probability_of_wrong_answer(200)
    p_blank=probability_of_blank_answer(200)
    
    # en az 8 soruyu boş yapma olasılığı, aynı zamanda diğerlerini de boş yapmayacak
    condition_prob = 0.0
    for b in range(8,101):
        condition_prob += combination(100,b)*p_blank**b*(1-p_blank)**(100-b)
    
    
    # 8 boş soru ve M den yüksek puan alma olasılığı(kesişim)
    n = 92    
    prob = 0.0
    for d in range(0,n+1): # 91 ye kadar doğru
        for y in range(0,n-d+1): # (n-doğru) kadar yanlış
            if point(d,y) > M:  # M den büyükse
                prob += probability_of_d_correct_and_y_wrong_answers(100, d, y, p_correct, p_wrong, p_blank)    
    
    return prob/condition_prob    

#---------------------------------------------part e------------------------------------------------------
def probability_of_getting_less_than_300_points():
    prob = 0.0
    correct_in_phase1=probability_of_correct_answer(200)
    wrong_in_phase1=probability_of_wrong_answer(200)
    blank_in_phase1=probability_of_blank_answer(200)
    n1=20

    correct_in_phase2=probability_of_correct_answer(156)
    wrong_in_phase2=probability_of_wrong_answer(156)
    blank_in_phase2=probability_of_blank_answer(156)
    n2=30
 
    correct_in_phase3=probability_of_correct_answer(110)
    wrong_in_phase3=probability_of_wrong_answer(110)
    blank_in_phase3=probability_of_blank_answer(110)
    n3=50
    
    for d1 in range(n1, -1, -1):
        for d2 in range(n2, -1, -1):
            if point(d1+d2+n3, 0) < 300:
                break  
            for d3 in range(n3, -1, -1):
                if point(d1+d2+d3, 0) < 300:
                    break
                for w1 in range(0,n1-d1+1):
                    if point(d1+d2+d3, w1) < 300:
                        break
                    for w2 in range(0,n2-d2+1):
                        if point(d1+d2+d3, w1+w2) < 300:
                            break
                        for w3 in range(0,n3-d3+1):
                            if point(d1+d2+d3, w1+w2+w3) < 300:
                                break
                            else:
                                prob += probability_of_d_correct_and_y_wrong_answers(n1, d1, w1, correct_in_phase1, wrong_in_phase1, blank_in_phase1) * probability_of_d_correct_and_y_wrong_answers(n2, d2, w2, correct_in_phase2, wrong_in_phase2, blank_in_phase2) * probability_of_d_correct_and_y_wrong_answers(n3, d3, w3, correct_in_phase3, wrong_in_phase3, blank_in_phase3)
    return 1-prob

#------------------------------------------------part f--------------------------------------------------------------

def probability_of_longer_than_25_minutes():
    #random.seed(1000)
    lambda_rate = 1/40
    probability_of_more_25 = cdf_of_exponential_distribution_from_a_to_b(25, 200, lambda_rate)
    print("f-) p1m25:",probability_of_more_25[0])
    probability=0.0
    x=np.arange(0,1000)
    y=[]
    for i in range(1,1001): 
        while True:
            ran =random.expovariate(lambda_rate)
            if ran <=200:
                break
        if ran> 25:
            probability += 1
        y.append(probability/i)
    plt.xlabel('Number of simulations') # x eksenine etiket
    plt.ylabel('Probability of greater than 25 minutes') # y eksenine etiket
    plt.title('count-probability') # başlık
    plt.bar(x,y)
    plt.savefig('countprob.png')
    plt.close()

#-------------------------------------------------part g-------------------------------------------------------
def probability_of_solution_time_of_second_30_questions_between_60_and_80_minutes():
    lambda_rate = 1/40
    integrate_erlang_distribution = integrate(lambda x: erlang_distribution(2,1/40,x), 100, 120)
    print("g-) b6080: ",integrate_erlang_distribution[0])

    x=np.arange(0,201)
    y=[]
    for i in range(0,201): 
       y.append(erlang_distribution(2,lambda_rate,i))
    

    plt.xlabel('x (minute)') # x eksenine etiket
    plt.ylabel('Probability of erlang distribution x') # y eksenine etiket
    plt.title('PDF of the Erlang distribution') # başlık
    plt.plot(x,y, color='purple')
    plt.savefig('erlangpdf.png')
    plt.close()

#-------------------------------main------------------------------------------------
#part a
save_png_of_probability_pistribution_of_correct_answer()
save_png_of_probability_pistribution_of_wrong_answer()
save_png_of_probability_pistribution_of_blank_answer()

#part b
M=float(input("Enter the M number: "))
print("b-) mtMor",M,": ",probability_of_getting_more_than_M_points(M))

#part c
print("c-) s: ",probability_of_passing_exam_successfully())

#part d
print("d-) news: ", probability_of_being_succesfull_with_8_blank_answers(M))

#part e
print("e-) lt300: ", probability_of_getting_less_than_300_points())

#part f
probability_of_longer_than_25_minutes()

#part g
probability_of_solution_time_of_second_30_questions_between_60_and_80_minutes()
