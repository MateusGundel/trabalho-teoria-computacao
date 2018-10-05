# -*- coding: utf-8 -*-
from django.shortcuts import render, redirect


# Create your views here.

def programa(request):
    if request.POST:
        tipo = request.POST['entrada1'] or None
    else:
        tipo = ''

    data = {}
    data["dados"] = tipo

    return render(request, 'web/programa.html', data)
