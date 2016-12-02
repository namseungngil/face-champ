﻿using UnityEditor;
using UnityEngine;
using System;
using System.Collections;
using System.Collections.Generic;
using System.Text;
using System.IO;
using MiniJSON;
using NPOI.HSSF.UserModel;
using NPOI.XSSF.UserModel;
using NPOI.SS.UserModel;
using Hellgate;

namespace HellgateEditor
{
    public enum ExcelImportType
    {
        NORMAL = 1,
        ATTRIBUTE
    }

    public class ExcelImportMaker
    {
        public delegate TResult TResultDelegate<out TResult> (string type);

        protected IWorkbook book;
        protected string excelFilePath;
        protected string outputJsonPath;
        protected TResultDelegate<Type> joinType;

        /// <summary>
        /// Initializes a new instance of the <see cref="HellgateEditor.ExcelImportMaker"/> class.
        /// </summary>
        /// <param name="excelFilePath">Excel file path.</param>
        /// <param name="outputJsonPath">Output json path.</param>
        public ExcelImportMaker (string excelFilePath, string outputJsonPath)
        {
            this.excelFilePath = excelFilePath;
            this.outputJsonPath = outputJsonPath;
        }

        private StringBuilder Append (List<string> keys, Dictionary<string, object> dic)
        {
            StringBuilder stringBuilder = new StringBuilder ();
            for (int i = 0; i < keys.Count; i++) {
                if (i == 0) {
                    stringBuilder.Append (dic [keys [i]]);
                } else {
                    stringBuilder.AppendFormat ("-{0}", dic [keys [i]]);
                }
            }

            return stringBuilder;
        }

        protected void AddData (ICell tCell, ICell vCell, Dictionary<string, object> dic)
        {
            if (vCell == null) {
                return;
            }

            if (vCell.CellType == CellType.String) {
                int n;
                if (int.TryParse (vCell.StringCellValue, out n)) {
                    dic.Add (tCell.StringCellValue, n);
                } else {
                    dic.Add (tCell.StringCellValue, vCell.StringCellValue);
                }
            } else if (vCell.CellType == CellType.Numeric) {
                dic.Add (tCell.StringCellValue, vCell.NumericCellValue);
            }
        }

        protected void CreateNormalJson (string[] ignores)
        {
            for (int i = 0; i < book.NumberOfSheets; ++i) {
                ISheet s = book.GetSheetAt (i);

                if (Array.IndexOf (ignores, s.SheetName) < 0) {
                    IRow titleRow = s.GetRow (0);
                    List<Dictionary<string, object>> list = new List<Dictionary<string, object>> ();

                    for (int j = 1; j <= s.LastRowNum; j++) {
                        Dictionary<string, object> dic = new Dictionary<string, object> ();
                        for (int k = 0; k <= titleRow.LastCellNum; k++) {
                            ICell tCell = titleRow.GetCell (k);
                            ICell vCell = s.GetRow (j).GetCell (k);

                            AddData (tCell, vCell, dic);
                        }
                        list.Add (dic);
                    }

                    EditorUtil.CreateJsonFile (s.SheetName, Json.Serialize (list), outputJsonPath);
                }
            }
        }

        protected void CreateAttributeJson ()
        {
            Type[] types = Reflection.GetExecutingAssembly ();
            foreach (Type type in types) {
                ExcelAttribute excel = type.GetAttributeValue<ExcelAttribute> ();
                if (excel == null || excel.CreateFileName == "") {
                    continue;
                }

                List<Dictionary<string, object>> list = CreateAttributeJson (type);
                if (list == null) {
                    continue;
                }

                if (excel.IndexFlag) {
                    AttributeMappingConfig<ColumnAttribute>[] configs = Reflection.FieldAMCRetrieve<ColumnAttribute> (type);
                    List<string> pks = new List<string> ();
                    List<string> fks = new List<string> ();
                    foreach (AttributeMappingConfig<ColumnAttribute> config in configs) {
                        if (config.t != null) {
                            ColumnAttribute column = config.t as ColumnAttribute;
                            if (column != null) {
                                if (column.CheckConstraints (DataConstraints.PK)) {
                                    pks.Add (config.name);
                                }

                                if (column.CheckConstraints (DataConstraints.FK)) {
                                    fks.Add (config.name);
                                }
                            }
                        }
                    }

                    if (pks.Count > 0) {
                        foreach (Dictionary<string, object> dic in list) {
                            StringBuilder stringBuilder = Append (pks, dic);
                            string createFileName = string.Format ("{0}{1}", excel.CreateFileName, stringBuilder.ToString ());
                            EditorUtil.CreateJsonFile (createFileName, Json.Serialize (dic), outputJsonPath);
                        }
                    }

                    if (fks.Count > 0) {
                        List<string> fk = new List<string> ();
                        foreach (Dictionary<string, object> dic in list) {
                            StringBuilder stringBuilder = Append (fks, dic);
                            fk.Add (stringBuilder.ToString ());
                        }

                        fk = Util.GetDistinctValues<string> (fk);
                        foreach (string s in fk) {
                            List<Dictionary<string, object>> fkList = new List<Dictionary<string, object>> ();
                            foreach (Dictionary<string, object> dic in list) {
                                StringBuilder stringBuilder = Append (fks, dic);
                                if (s == stringBuilder.ToString ()) {
                                    fkList.Add (dic);
                                }
                            }

                            string createFileName = string.Format ("{0}{1}", excel.CreateFileName, s);
                            EditorUtil.CreateJsonFile (createFileName, Json.Serialize (fkList), outputJsonPath);
                        }
                    }
                } else {
                    if (list != null && list.Count > 0) {
                        EditorUtil.CreateJsonFile (excel.CreateFileName, Json.Serialize (list), outputJsonPath);
                    }
                }
            }
        }

        protected Dictionary<string, object> CreateAttributeJson (IRow titleRow,
                                                                  IRow row,
                                                                  AttributeMappingConfig<ColumnAttribute>[] configs,
                                                                  Dictionary<string, object> joinColumn)
        {
            Dictionary<string, object> sheetDic = new Dictionary<string, object> ();
            for (int k = 0; k <= titleRow.LastCellNum; k++) {
                ICell tCell = titleRow.GetCell (k);
                ICell vCell = row.GetCell (k);

                AddData (tCell, vCell, sheetDic);
            }


            Dictionary<string, object> dic = new Dictionary<string, object> ();
            foreach (AttributeMappingConfig<ColumnAttribute> config in configs) {
                if (Util.IsValueType (config.type)) {
                    if (sheetDic.ContainsKey (Util.ConvertCamelToUnderscore (config.name))) {
                        if (joinColumn != null) {
                            foreach (KeyValuePair<string, object> pair in joinColumn) {
                                if (pair.Key == config.name) {
                                    if (pair.Value.ToString () != sheetDic [Util.ConvertCamelToUnderscore (config.name)].ToString ()) {
                                        return null;
                                    }
                                }
                            }
                        }

                        dic.Add (config.name, sheetDic [Util.ConvertCamelToUnderscore (config.name)]);
                    }
                } else {
                    if (!config.type.IsArray) {
                        continue;
                    }

                    Type type = config.type.GetElementType ();
                    string value = "";
                    if (config.t != null) {
                        ColumnAttribute column = config.t as ColumnAttribute;
                        if (column != null) {
                            if (column.Value == "" && column.Type != "") {
                                value = config.name;
                            } else if (joinType != null) {
                                Type tempType = joinType (sheetDic [column.Type].ToString ());
                                type = tempType == null ? type : tempType;
                                value = column.Value;
                            }
                        }
                    }

                    Dictionary<string, object> jDic = new Dictionary<string, object> ();
                    AttributeMappingConfig<ColumnAttribute>[] temp = Reflection.FieldAMCRetrieve<ColumnAttribute> (config.type.GetElementType ());
                    foreach (AttributeMappingConfig<ColumnAttribute> c in temp) {
                        if (c.t != null) {
                            ColumnAttribute column = c.t as ColumnAttribute;
                            if (column != null) {
                                if (column.CheckConstraints (DataConstraints.PK)) {
                                    jDic.Add (c.name, sheetDic [value]);
                                    break;
                                }

                                if (sheetDic.ContainsKey (column.Value)) {
                                    jDic.Add (c.name, sheetDic [column.Value]);
                                }
                            }
                        }
                    }

                    if (jDic.Count > 0) {
                        dic.Add (config.name, CreateAttributeJson (type, jDic));
                    }
                }
            }

            return dic;
        }

        protected List<Dictionary<string, object>> CreateAttributeJson (Type type, Dictionary<string, object> joinColumn = null)
        {
            ExcelAttribute excel = type.GetAttributeValue<ExcelAttribute> ();
            AttributeMappingConfig<ColumnAttribute>[] configs = Reflection.FieldAMCRetrieve<ColumnAttribute> (type);

            if (configs.Length > 0) {
                if (excel == null) {
                    return null;
                }

                ISheet s = book.GetSheet (excel.SheetName);
                if (s == null) {
                    HDebug.LogWarning (type.Name + " sheet name is incorrect.");
                    return null;
                }
                IRow titleRow = s.GetRow (0);

                List<Dictionary<string, object>> list = new List<Dictionary<string, object>> ();

                for (int i = 1; i <= s.LastRowNum; i++) {
                    Dictionary<string, object> dic = CreateAttributeJson (titleRow, s.GetRow (i), configs, joinColumn);

                    if (dic != null && dic.Count > 0) {
                        list.Add (dic);
                    }
                }

                return list;
            } else {
                return null;
            }
        }

        /// <summary>
        /// Create Json the specified type and ignores.
        /// </summary>
        /// <param name="type">Type.</param>
        /// <param name="ignores">Ignores.</param>
        public void Create (ExcelImportType type, string[] ignores = null)
        {
            using (FileStream stream = new FileStream (excelFilePath, FileMode.Open, FileAccess.Read, FileShare.ReadWrite)) {
                if (excelFilePath.EndsWith ("xls")) {
                    book = new HSSFWorkbook (stream);
                } else if (excelFilePath.EndsWith ("xlsx")) {
                    if (Application.platform == RuntimePlatform.OSXEditor) {
                        Debug.LogWarning ("xlsx is not supported on OSX.");
                        return;
                    } else {
                        book = new XSSFWorkbook (stream);
                    }
                } else {
                    return;
                }

                if (type == ExcelImportType.NORMAL) {
                    CreateNormalJson (ignores);
                } else {
                    CreateAttributeJson ();
                }
            }
        }
    }
}
