# -*- coding: utf-8 -*-
from django.shortcuts import render, redirect
from code import processamento


# Create your views here.

def programa(request):
    if request.POST:
        tipo = request.POST['entrada1'] or None
        print(tipo)
        print(processamento.to_words(tipo))
    else:
        tipo = ''

    data = {}
    data["dados"] = tipo

    return render(request, 'web/programa.html', data)